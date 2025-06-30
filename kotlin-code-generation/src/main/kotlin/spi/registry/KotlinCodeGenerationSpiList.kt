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

  /**
   * Gives all strategies from this list.
   */
  val strategies: KotlinCodeGenerationStrategyList by lazy {
    KotlinCodeGenerationStrategyList(list.filterIsInstance<UnboundKotlinCodeGenerationStrategy>())
  }

  /**
   * Gives all processors from this list.
   */
  val processors: KotlinCodeGenerationProcessorList by lazy {
    KotlinCodeGenerationProcessorList(list.filterIsInstance<UnboundKotlinCodeGenerationProcessor>())
  }

  /**
   * Returns a new [KotlinCodeGenerationSpiList] containing only the strategies and processors that match the given predicate.
   *
   * @param filter the predicate to filter the strategies and processors.
   * @return a new [KotlinCodeGenerationSpiList] containing only the matching strategies and processors.
   */
  fun filter(filter: KotlinCodeGenerationSpiPredicate): KotlinCodeGenerationSpiList = copy(
    list = list.filter { filter.test(it) }
  )

  /**
   * Returns a new [KotlinCodeGenerationSpiList] containing only the strategies and processors that do _not_ match the given predicate.
   *
   * @param filter the predicate to filter the strategies and processors.
   * @return a new [KotlinCodeGenerationSpiList] containing only the non-matching strategies and processors.
   */
  fun filterNot(filter: KotlinCodeGenerationSpiPredicate): KotlinCodeGenerationSpiList = copy(
    list = list.filter { !filter.test(it) }
  )

  override fun toString(): String = "KotlinCodeGenerationSpiList(list=${list.map { it.name }})"

  /**
   * Creates a [KotlinCodeGenerationSpiRegistry] from this list.
   *
   * @return a [KotlinCodeGenerationSpiRegistry] containing the strategies and processors from this list.
   */
  fun registry() : KotlinCodeGenerationSpiRegistry = DefaultKotlinCodeGenerationServiceRegistry(
    strategies = strategies,
    processors = processors
  )
}
