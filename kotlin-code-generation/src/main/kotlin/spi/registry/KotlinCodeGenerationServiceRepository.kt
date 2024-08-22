package io.toolisticon.kotlin.generation.spi.registry

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spi.*
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import mu.KLogging
import java.util.*
import kotlin.reflect.KClass

/**
 * Holds all implementation instances of [KotlinCodeGenerationStrategy] and [KotlinCodeGenerationProcessor].
 *
 * Main Use-Case is loading these instances via [ServiceLoader] (implemented in [KotlinCodeGenerationServiceLoader],
 * or short by [io.toolisticon.kotlin.generation.KotlinCodeGeneration.spi.registry].
 */
@ExperimentalKotlinPoetApi
class KotlinCodeGenerationServiceRepository(
  override val contextTypeUpperBound: KClass<*>,
  override val processors: KotlinCodeGenerationProcessorList = KotlinCodeGenerationProcessorList(),
  override val strategies: KotlinCodeGenerationStrategyList = KotlinCodeGenerationStrategyList(),
) : KotlinCodeGenerationSpiRegistry {
  companion object : KLogging() {

    fun load(contextTypeUpperBound: KClass<*>, classLoader: ClassLoader = KotlinCodeGeneration.spi.defaultClassLoader()): KotlinCodeGenerationSpiRegistry {
      return KotlinCodeGenerationServiceLoader(contextTypeUpperBound, classLoader)()
    }

  }

  init {
    require(strategies.isNotEmpty()) { "At least one strategy is required." }
    if (processors.isEmpty()) {
      logger.info { "No processors have been registered." }
    }
  }

  override fun toString(): String = "${this::class.simpleName}(" +
    "contextType=$contextTypeUpperBound, " +
    "strategies=${strategies}, " +
    "processors=${processors})"
}
