package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationServiceRepository
import io.toolisticon.kotlin.generation.spi.strategy.KotlinDataClassSpecStrategy
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
internal class KotlinCodeGenerationServiceRepositoryTest {

  class TestContext(registry: KotlinCodeGenerationSpiRegistry) : KotlinCodeGenerationContextBase<TestContext>(registry) {
    override val contextType = TestContext::class
  }

  class TestInput

  class FooDataClassStrategy : KotlinDataClassSpecStrategy<TestContext, TestInput>(
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
    val registry = KotlinCodeGenerationServiceRepository(contextTypeUpperBound = TestContext::class, strategies = KotlinCodeGenerationStrategyList(FooDataClassStrategy()))

    println(registry)
  }

  object SealedSuperContext {

    sealed interface TestType {
      data class LongType(val value: Long) : TestType
      data class StringType(val value: String) : TestType
    }

    sealed interface SuperContext {
      class SubContextLong(registry: KotlinCodeGenerationSpiRegistry) : SuperContext, KotlinCodeGenerationContextBase<SubContextLong>(registry) {
        override val contextType: KClass<SubContextLong> = SubContextLong::class
      }

      class SubContextString(registry: KotlinCodeGenerationSpiRegistry) : SuperContext, KotlinCodeGenerationContextBase<SubContextString>(registry) {
        override val contextType: KClass<SubContextString> = SubContextString::class
      }
    }

    class LongDataClassStrategy : KotlinDataClassSpecStrategy<SuperContext.SubContextLong, String>(SuperContext.SubContextLong::class, String::class) {
      override fun invoke(context: SuperContext.SubContextLong, input: String): KotlinDataClassSpec = KotlinCodeGeneration.buildDataClass(KotlinCodeGeneration.className("foo.bar", "LongClass")) {
        addConstructorProperty(input, Long::class)
      }
    }

    class StringDataClassStrategy : KotlinDataClassSpecStrategy<SuperContext.SubContextString, String>(SuperContext.SubContextString::class, String::class) {
      override fun invoke(context: SuperContext.SubContextString, input: String): KotlinDataClassSpec = KotlinCodeGeneration.buildDataClass(KotlinCodeGeneration.className("foo.bar", "StringClass")) {
        addConstructorProperty(input, Long::class)
      }
    }
  }


  @Test
  fun `initialize with sealed super context`() {
    val registry = KotlinCodeGenerationServiceRepository(
      contextTypeUpperBound = SealedSuperContext.SuperContext::class,
      strategies = KotlinCodeGenerationStrategyList(SealedSuperContext.LongDataClassStrategy(), SealedSuperContext.StringDataClassStrategy())
    )

    assertThat(registry.strategies.filter(SealedSuperContext.LongDataClassStrategy::class)).isNotEmpty

    val longContext = SealedSuperContext.SuperContext.SubContextLong(registry)
    val longDataClass = longContext.registry.strategies.filter(SealedSuperContext.LongDataClassStrategy::class).single()
      .invoke(longContext, "foo")

    assertThat(longDataClass.code).isEqualToIgnoringWhitespace(
      """public data class LongClass(
               public val foo: kotlin.Long,
         )"""
    )
  }
}
