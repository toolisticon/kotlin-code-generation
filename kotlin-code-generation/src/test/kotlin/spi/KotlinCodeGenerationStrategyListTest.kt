package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.TestFixtures.SpiFixtures.DataClassAStrategy
import io.toolisticon.kotlin.generation.TestFixtures.SpiFixtures.DataClassBStrategy
import io.toolisticon.kotlin.generation.TestFixtures.SpiFixtures.EmptyContext
import io.toolisticon.kotlin.generation.TestFixtures.SpiFixtures.InputA
import io.toolisticon.kotlin.generation.TestFixtures.SpiFixtures.InputB
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import io.toolisticon.kotlin.generation.spi.strategy.executeAll
import io.toolisticon.kotlin.generation.spi.strategy.executeSingle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
class KotlinCodeGenerationStrategyListTest {

  class AStrategy : DataClassAStrategy() {
    override fun invoke(context: EmptyContext, input: InputA): KotlinDataClassSpec = buildDataClass(input.className) {
      input.fields.map { (k, v) -> buildConstructorProperty(k, v) }.forEach(this::addConstructorProperty)
    }
  }

  class BStrategy : DataClassBStrategy() {
    override fun invoke(context: EmptyContext, input: InputB): KotlinDataClassSpec = buildDataClass(input.className) {
      input.fields.map { (k, v) -> buildConstructorProperty(k + "B", v) }.forEach(this::addConstructorProperty)
    }
  }

  @Test
  fun `execute single strategy`() {
    val list = KotlinCodeGenerationStrategyList(AStrategy())
    assertThat(list).hasSize(1)

    val strategies = list.filter(AStrategy::class)

    val dataClass = strategies.executeSingle(
      context = EmptyContext,
      input = InputA(packageName = "foo", simpleName = "Bar", fields = mapOf("x" to Int::class))
    )

    assertThat(dataClass).isNotNull
    assertThat(dataClass!!.code).isEqualToIgnoringWhitespace("public data class Bar(  public val x: kotlin.Int,)")
  }

  @Test
  fun `switch between a and b`() {
    val list = KotlinCodeGenerationStrategyList(AStrategy(), BStrategy())
    assertThat(list).hasSize(2)

    val strategy = list.filter(AStrategy::class).single()

    val dataClass = strategy.execute(
      context = EmptyContext,
      input = InputA(packageName = "foo", simpleName = "Bar", fields = mapOf("x" to Int::class))
    )

    assertThat(dataClass).isNotNull
    assertThat(dataClass!!.code).isEqualToIgnoringWhitespace("public data class Bar(  public val x: kotlin.Int,)")

    assertThat(
      list.filter(BStrategy::class).single()
        .execute(EmptyContext, InputB(className("b", "B"), mapOf("y" to Long::class)))!!
        .code
    ).isEqualToIgnoringWhitespace("public data class B(  public val yB: kotlin.Long,)")
  }

  @Test
  fun `execute two of three strategies`() {
    val input = InputA(packageName = "foo", simpleName = "Bar", fields = mapOf("x" to Int::class))
    val list = KotlinCodeGenerationStrategyList(
      AStrategy(),
      object : DataClassAStrategy() {
        override fun invoke(context: EmptyContext, input: InputA): KotlinDataClassSpec = buildDataClass(input.className) {
          input.fields.map { (k, v) -> buildConstructorProperty(k, v) }.forEach(this::addConstructorProperty)
        }

        override fun test(context: EmptyContext, input: Any?): Boolean = false
      },
      object : DataClassAStrategy() {
        override fun invoke(context: EmptyContext, input: InputA): KotlinDataClassSpec = buildDataClass(className(input.packageName, input.simpleName + "XXX")) {
          input.fields.map { (k, v) -> buildConstructorProperty(k, v) }.forEach(this::addConstructorProperty)
        }
      },
    )
    assertThat(list).hasSize(3)
    assertThat(list.filter(DataClassAStrategy::class)).hasSize(3)


    val specs = list.filter(DataClassAStrategy::class).executeAll(EmptyContext, input)
    assertThat(specs).hasSize(2)
  }
}
