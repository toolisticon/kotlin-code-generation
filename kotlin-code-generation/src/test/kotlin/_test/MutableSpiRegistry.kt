
package io.toolisticon.kotlin.generation._test

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import kotlin.reflect.KClass

@OptIn(ExperimentalKotlinPoetApi::class)
class MutableSpiRegistry(
  val strategyList: MutableList<UnboundKotlinCodeGenerationStrategy> = mutableListOf(),
  val processorList: MutableList<UnboundKotlinCodeGenerationProcessor> = mutableListOf(),
) : KotlinCodeGenerationSpiRegistry {
  override val contextTypeUpperBound: KClass<*> = TestContext::class

  override val strategies: KotlinCodeGenerationStrategyList get() = KotlinCodeGenerationStrategyList(strategyList)
  override val processors: KotlinCodeGenerationProcessorList get() = KotlinCodeGenerationProcessorList(processorList)
}
