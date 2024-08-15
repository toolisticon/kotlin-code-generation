package io.toolisticon.kotlin.generation.spi

import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationServiceRepository
import io.toolisticon.kotlin.generation.spi.strategy.DataClassSpecStrategy
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

internal class DefaultAbstractKotlinCodeGenerationSpiRegistryTest {

  class TestContext(override val registry: AbstractKotlinCodeGenerationSpiRegistry) : KotlinCodeGenerationContext {
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
    val registry = KotlinCodeGenerationServiceRepository(contextType = TestContext::class, FooDataClassStrategy())

    println(registry)
  }

  object SealedSuperContext {
    sealed interface TestType {
      data class LongType(val value: Long) : TestType
      data class StringType(val value: String) : TestType
    }

    sealed class SuperContext<T : TestType>(override val registry: AbstractKotlinCodeGenerationSpiRegistry) : KotlinCodeGenerationContext {
      override val contextType: KClass<SuperContext<*>> = SuperContext::class

      class SubContextLong(registry: AbstractKotlinCodeGenerationSpiRegistry) : SuperContext<TestType.LongType>(registry) {
        override val contextType: KClass<SuperContext<*>> = SubContextLong::class
      }
      class SubContextString(registry: AbstractKotlinCodeGenerationSpiRegistry) : SuperContext<TestType.StringType>(registry)
    }

    class GenericFileSpec
  }


  @Test
  fun `initialize with sealed super context`() {

  }
}
