package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
interface KotlinCodeGenerationSpiRegistry {
  val contextTypeUpperBound: KClass<*>

  val strategies: KotlinCodeGenerationStrategyList
  val processors: KotlinCodeGenerationProcessorList

}
