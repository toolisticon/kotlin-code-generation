package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationSpiList
import java.util.function.Predicate
import java.util.function.Supplier

/**
 * Use to express that a processor does not work on an input loop variable.
 */
data object EmptyInput

@OptIn(ExperimentalKotlinPoetApi::class)
fun interface KotlinCodeGenerationSpiListSupplier : Supplier<KotlinCodeGenerationSpiList>, () -> KotlinCodeGenerationSpiList {
  override fun get(): KotlinCodeGenerationSpiList = invoke()
  override fun invoke(): KotlinCodeGenerationSpiList
}

/**
 * Convenience alias to reference unbound strategies without repeating the `<*,*,*>`.
 */
@ExperimentalKotlinPoetApi
typealias UnboundKotlinCodeGenerationSpi = KotlinCodeGenerationSpi<*, *>

/**
 * Convenience alias to reference unbound strategies without repeating the `<*,*,*>`.
 */
@ExperimentalKotlinPoetApi
typealias UnboundKotlinCodeGenerationStrategy = KotlinCodeGenerationStrategy<*, *, *>

/**
 * Convenience alias to reference unbound processors without repeating the `<*,*,*>`.
 */
@ExperimentalKotlinPoetApi
typealias UnboundKotlinCodeGenerationProcessor = KotlinCodeGenerationProcessor<*, *, *>


@ExperimentalKotlinPoetApi
typealias KotlinCodeGenerationSpiPredicate = Predicate<UnboundKotlinCodeGenerationSpi>

