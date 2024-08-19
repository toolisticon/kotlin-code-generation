package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spi.context.AbstractKotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationServiceRepository
import io.toolisticon.kotlin.generation.spi.strategy.DataClassSpecStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
internal class KotlinCodeGenerationServiceRepositoryTest {

  class TestContext(registry: KotlinCodeGenerationSpiRegistry) : AbstractKotlinCodeGenerationContext<TestContext>(registry) {
    override val contextType = TestContext::class
  }

  class TestInput

  class FooDataClassStrategy : DataClassSpecStrategy<TestContext, TestInput>(
    contextType = TestContext::class,
    inputType = TestInput::class,
  ) {
    override fun invoke(context: TestContext, input: TestInput): KotlinDataClassSpec {
      TODO("Not yet implemented")
    }

    override val specType: KClass<KotlinDataClassSpec> = KotlinDataClassSpec::class

  }


  @Test
  fun `create registry from spi instances`() {
    val registry = KotlinCodeGenerationServiceRepository(contextTypeUpperBound = TestContext::class, FooDataClassStrategy())

    println(registry)
  }

  object SealedSuperContext {

    sealed interface TestType {
      data class LongType(val value: Long) : TestType
      data class StringType(val value: String) : TestType
    }

    sealed interface SuperContext {
      class SubContextLong(registry: KotlinCodeGenerationSpiRegistry) : SuperContext, AbstractKotlinCodeGenerationContext<SubContextLong>(registry) {
        override val contextType: KClass<SubContextLong> = SubContextLong::class
      }

      class SubContextString(registry: KotlinCodeGenerationSpiRegistry) : SuperContext, AbstractKotlinCodeGenerationContext<SubContextString>(registry) {
        override val contextType: KClass<SubContextString> = SubContextString::class
      }
    }

    class LongDataClassStrategy : DataClassSpecStrategy<SuperContext.SubContextLong, String>(SuperContext.SubContextLong::class, String::class) {
      override fun invoke(context: SuperContext.SubContextLong, input: String): KotlinDataClassSpec = KotlinCodeGeneration.buildDataClass(KotlinCodeGeneration.className("foo.bar", "LongClass")) {
        addConstructorProperty(input, Long::class)
      }
    }
    class StringDataClassStrategy : DataClassSpecStrategy<SuperContext.SubContextString, String>(SuperContext.SubContextString::class, String::class) {
      override fun invoke(context: SuperContext.SubContextString, input: String): KotlinDataClassSpec = KotlinCodeGeneration.buildDataClass(KotlinCodeGeneration.className("foo.bar", "StringClass")) {
        addConstructorProperty(input, Long::class)
      }
    }
  }


  @Test
  fun `initialize with sealed super context`() {
    val registry = KotlinCodeGenerationServiceRepository(contextTypeUpperBound = SealedSuperContext.SuperContext::class, SealedSuperContext.LongDataClassStrategy(), SealedSuperContext.StringDataClassStrategy())


    assertThat(registry.findStrategies(SealedSuperContext.SuperContext.SubContextLong::class, String::class, KotlinDataClassSpec::class)).isNotEmpty

    val longContext = SealedSuperContext.SuperContext.SubContextLong(registry)
    val longDataClass = longContext.findStrategies(inputType = String::class, specType = KotlinDataClassSpec::class).single()
      .invoke(longContext, "foo")

    assertThat(longDataClass.code).isEqualToIgnoringWhitespace(
      """public data class LongClass(
               public val foo: kotlin.Long,
         )"""
    )
  }
}
