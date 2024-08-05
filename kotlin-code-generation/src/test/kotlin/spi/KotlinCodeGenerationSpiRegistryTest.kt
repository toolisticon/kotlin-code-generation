package io.toolisticon.kotlin.generation.spi

import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.strategy.DataClassSpecStrategy
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

internal class KotlinCodeGenerationSpiRegistryTest {

  class TestContext(registry: KotlinCodeGenerationSpiRegistry<TestContext>) : AbstractKotlinCodeGenerationContext<TestContext>(registry) {

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
    val registry = KotlinCodeGenerationSpiRegistry<TestContext>(contextType = TestContext::class, FooDataClassStrategy())

    println(registry)
  }
}
