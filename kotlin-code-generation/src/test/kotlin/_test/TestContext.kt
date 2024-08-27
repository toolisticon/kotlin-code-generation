@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation._test

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import kotlin.reflect.KClass

class TestContext(
  val rootClassName: ClassName
) : KotlinCodeGenerationContext<TestContext> {


  val strategyList = mutableListOf<UnboundKotlinCodeGenerationStrategy>()
  val processorList = mutableListOf<UnboundKotlinCodeGenerationProcessor>()

  override val contextType = TestContext::class
  override val registry: KotlinCodeGenerationSpiRegistry = object : KotlinCodeGenerationSpiRegistry {
    override val contextTypeUpperBound: KClass<*> = TestContext::class

    override val strategies: KotlinCodeGenerationStrategyList = KotlinCodeGenerationStrategyList(strategyList)
    override val processors: KotlinCodeGenerationProcessorList = KotlinCodeGenerationProcessorList(processorList)
  }
}
