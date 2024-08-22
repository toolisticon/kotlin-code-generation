package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import kotlin.reflect.KClass

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
   * All spi instances define a [KotlinCodeGenerationSpi.contextType] to indicate the context
   * they are operating on. The registries upper bound defines what contextTypes are allowed in this
   * registry. Especially useful when dealing with context hierarchies.
   */
  val contextTypeUpperBound: KClass<*>

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
