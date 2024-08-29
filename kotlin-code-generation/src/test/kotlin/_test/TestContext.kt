@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation._test

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext

class TestContext(
  val rootClassName: ClassName,
  override val registry: MutableSpiRegistry
) : KotlinCodeGenerationContext<TestContext> {
  override val contextType = TestContext::class
}
