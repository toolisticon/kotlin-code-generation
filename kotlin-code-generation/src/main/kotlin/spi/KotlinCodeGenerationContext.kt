package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
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

  fun <INPUT : Any, SPEC : Any> findStrategies(inputType: KClass<INPUT>, specType: KClass<SPEC>): KotlinCodeGenerationStrategyList<SELF, INPUT, SPEC>
  fun <INPUT : Any> findStrategies(inputType: KClass<INPUT>): KotlinCodeGenerationStrategyList<SELF, INPUT, *>
  fun findStrategies(): KotlinCodeGenerationStrategyList<SELF, *, *>

  fun <INPUT : Any, SPEC : Any> findProcessors(inputType: KClass<INPUT>, specType: KClass<SPEC>): KotlinCodeGenerationProcessorList<SELF, INPUT, SPEC>
  fun <INPUT : Any> findProcessors(inputType: KClass<INPUT>): KotlinCodeGenerationProcessorList<SELF, INPUT, *>
  fun findProcessors(): KotlinCodeGenerationProcessorList<SELF, *, *>
}
