package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass

/**
 * Context used for SPI execution. Typically, holds mutable state that is modified while processing the chain.
 * Required because all spi-instances have to be generated via default-constructor, so all state data
 * we would normally store in a property has to be passed to every function call.
 */
@ExperimentalKotlinPoetApi
interface KotlinCodeGenerationContext<SELF : KotlinCodeGenerationContext<SELF>> {

  val contextType: KClass<SELF>
  val registry: KotlinCodeGenerationSpiRegistry

  fun <PROCESSOR : KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> processors(
    processorType: KClass<PROCESSOR>
  ): List<PROCESSOR> {
    return registry.processors.filterIsInstance(processorType.java)
  }

  fun <STRATEGY : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> strategies(
    strategyType: KClass<STRATEGY>
  ): List<STRATEGY> {
    return registry.strategies.filterIsInstance(strategyType.java)
  }
}
