package io.toolisticon.kotlin.generation.itest.spi

import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.AbstractKotlinCodeGenerationSpiRegistry
import kotlin.reflect.KClass

class TestContext(override val registry: AbstractKotlinCodeGenerationSpiRegistry) : KotlinCodeGenerationContext {
  override val contextType: KClass<*> = TestContext::class
}
