package io.toolisticon.kotlin.generation.spi.registry

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.github.oshai.kotlinlogging.KotlinLogging
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import java.util.*

/**
 * Holds all implementation instances of [KotlinCodeGenerationStrategy] and [KotlinCodeGenerationProcessor].
 *
 * Main Use-Case is loading these instances via [ServiceLoader] (implemented in [KotlinCodeGenerationServiceLoader],
 * or short by [io.toolisticon.kotlin.generation.KotlinCodeGeneration.spi.registry].
 */
@ExperimentalKotlinPoetApi
data class KotlinCodeGenerationServiceRepository(
  override val processors: KotlinCodeGenerationProcessorList = KotlinCodeGenerationProcessorList(),
  override val strategies: KotlinCodeGenerationStrategyList = KotlinCodeGenerationStrategyList(),
) : KotlinCodeGenerationSpiRegistry {

  constructor(spi: KotlinCodeGenerationSpiList) : this(
    processors = spi.processors,
    strategies = spi.strategies,
  )

  private val logger = KotlinLogging.logger {}

  init {
    require(strategies.isNotEmpty()) { "At least one strategy is required." }
    if (processors.isEmpty()) {
      logger.info { "No processors have been registered." }
    }
  }

  override fun toString(): String = "${this::class.simpleName}(" +
    "strategies=${strategies}, " +
    "processors=${processors})"
}
