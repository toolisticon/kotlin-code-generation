package io.toolisticon.kotlin.generation._test

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContextFactory

@OptIn(ExperimentalKotlinPoetApi::class)
class TestContext(
  val rootClassName: ClassName,
  override val registry: MutableSpiRegistry
) : KotlinCodeGenerationContext<TestContext> {
  override val contextType = TestContext::class
}

@OptIn(ExperimentalKotlinPoetApi::class)
fun testContextFactory(registry: MutableSpiRegistry): KotlinCodeGenerationContextFactory<TestContext, TestDeclaration> = KotlinCodeGenerationContextFactory {
  TestContext(rootClassName = it.rootClassName, registry = registry)
}
