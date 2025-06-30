package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList

/**
 * The registry provides access to all registered [KotlinCodeGenerationSpi] instances.
 * For simplified usage, the instances are separated into [strategies] and [processors].
 *
 * While [processors] or optional, [strategies] must not be empty, the concept of code generation
 * is useless without at least on build plan.
 *
 * Hint: Extracted to interface, so we could have multiple implementations. The main use case will be to wrap the
 * results of [java.util.ServiceLoader], see [io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationServiceLoader].
 */
@ExperimentalKotlinPoetApi
interface KotlinCodeGenerationSpiRegistry {
  /**
   * All registered [KotlinCodeGenerationStrategy] instances, wrapped in a [KotlinCodeGenerationStrategyList].
   * Must not be empty.
   */
  val strategies: KotlinCodeGenerationStrategyList

  /**
   * All registered [KotlinCodeGenerationProcessor] instances, wrapped in a [KotlinCodeGenerationProcessorList].
   * Might be empty.
   */
  val processors: KotlinCodeGenerationProcessorList
}
