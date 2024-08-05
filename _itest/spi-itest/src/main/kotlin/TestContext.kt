package io.toolisticon.kotlin.generation.itest.spi

import io.toolisticon.kotlin.generation.spi.AbstractKotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

class TestContext(registry: KotlinCodeGenerationSpiRegistry<TestContext>) : AbstractKotlinCodeGenerationContext<TestContext>(registry) {
}
