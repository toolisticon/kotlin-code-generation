package io.toolisticon.kotlin.generation.itest.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

@ExperimentalKotlinPoetApi
class TestContext(registry: KotlinCodeGenerationSpiRegistry) : KotlinCodeGenerationContextBase<TestContext>(registry) {
  override val contextType = TestContext::class
}
