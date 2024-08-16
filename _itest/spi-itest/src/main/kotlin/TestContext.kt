package io.toolisticon.kotlin.generation.itest.spi

import io.toolisticon.kotlin.generation.context.AbstractKotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

class TestContext(registry: KotlinCodeGenerationSpiRegistry) : AbstractKotlinCodeGenerationContext<TestContext>(registry) {
  override val contextType = TestContext::class
}
