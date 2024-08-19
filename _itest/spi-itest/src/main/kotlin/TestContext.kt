package io.toolisticon.kotlin.generation.itest.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.context.AbstractKotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

@ExperimentalKotlinPoetApi
class TestContext(registry: KotlinCodeGenerationSpiRegistry) : AbstractKotlinCodeGenerationContext<TestContext>(registry) {
  override val contextType = TestContext::class
}
