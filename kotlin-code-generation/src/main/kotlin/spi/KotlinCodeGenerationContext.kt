package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass

/**
 * Context used for SPI execution, providing additional information that is not transported via the `input`
 * work item. Might hold mutable state shared by strategies and processors to transport data on the fly.
 *
 * Required because all spi-instances have to be generated via default-constructor, so all state data
 * we would normally store in a property has to be passed to every function call.
 *
 * Hint: for implementing a concrete context, use the [io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase].
 */
@ExperimentalKotlinPoetApi
interface KotlinCodeGenerationContext<SELF : KotlinCodeGenerationContext<SELF>> {

  /**
   * SELF type of the context implementation, used to verify against [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi.contextType].
   */
  val contextType: KClass<SELF>

  /**
   * The registry holding strategies and processors so implementations can nest calls to other instances.
   */
  val registry: KotlinCodeGenerationSpiRegistry

  /**
   * Convenience access to [io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList.filterIsInstance].
   */
  fun <STRATEGY : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> strategies(
    strategyType: KClass<STRATEGY>
  ): List<STRATEGY> = registry.strategies.filterIsInstance(strategyType.java)

  /**
   * Convenience access to [io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList.filterIsInstance].
   */
  fun <PROCESSOR : KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> processors(
    processorType: KClass<PROCESSOR>
  ): List<PROCESSOR> = registry.processors.filterIsInstance(processorType.java)
}

@OptIn(ExperimentalKotlinPoetApi::class)
fun interface KotlinCodeGenerationContextFactory<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> : (INPUT) -> CONTEXT
