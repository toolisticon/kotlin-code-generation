package io.toolisticon.kotlin.generation.spi.registry

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.*
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList

/**
 * A list of [UnboundKotlinCodeGenerationSpi] that implements the [KotlinCodeGenerationSpiRegistry] interface.
 *
 * These are loaded via SPI using [java.util.ServiceLoader], implemented via [KotlinCodeGenerationSpiListSupplier].
 */
@ExperimentalKotlinPoetApi
@Suppress("JavaDefaultMethodsNotOverriddenByDelegation")
data class KotlinCodeGenerationSpiList(private val list: List<UnboundKotlinCodeGenerationSpi>) : List<UnboundKotlinCodeGenerationSpi> by list {

  /**
   * Creates a [KotlinCodeGenerationSpiList] from the given strategies and processors.
   *
   * @param strategy the strategies to include in the list.
   */
  constructor(vararg strategy: UnboundKotlinCodeGenerationSpi) : this(strategy.toList())

  val strategies: KotlinCodeGenerationStrategyList by lazy {
    KotlinCodeGenerationStrategyList(list.filterIsInstance<UnboundKotlinCodeGenerationStrategy>())
  }

  val processors: KotlinCodeGenerationProcessorList by lazy {
    KotlinCodeGenerationProcessorList(list.filterIsInstance<UnboundKotlinCodeGenerationProcessor>())
  }

  fun filter(filter: KotlinCodeGenerationSpiPredicate): KotlinCodeGenerationSpiList = copy(
    list = list.filter { filter.test(it) }
  )

  fun filterNot(filter: KotlinCodeGenerationSpiPredicate): KotlinCodeGenerationSpiList = copy(
    list = list.filter { !filter.test(it) }
  )

  override fun toString(): String = "KotlinCodeGenerationSpiList(list=${list.map { it.name }})"

  fun registry() : KotlinCodeGenerationSpiRegistry = DefaultKotlinCodeGenerationServiceRegistry(
    strategies = strategies,
    processors = processors
  )
}
