package io.toolisticon.kotlin.generation.spi

import kotlin.reflect.KClass

/**
 * Context used for SPI execution. Typically, holds mutable state that is modified while processing the chain.
 * Required because all spi-instances have to be generated via default-constructor, so all state data
 * we would normally store in a property has to be passed to every function call.
 */
interface KotlinCodeGenerationContext<SELF : KotlinCodeGenerationContext<SELF>> {

  val contextType: KClass<SELF>



  fun <INPUT : Any, SPEC : Any> findStrategies(inputType: KClass<INPUT>, specType: KClass<SPEC>): List<KotlinCodeGenerationStrategy<SELF, INPUT, SPEC>>
  fun <INPUT : Any> findStrategies(inputType: KClass<INPUT>): List<KotlinCodeGenerationStrategy<SELF, INPUT, *>>
  fun findStrategies(): List<KotlinCodeGenerationStrategy<SELF, *, *>>


  fun <INPUT : Any, SPEC : Any> findProcessors(inputType: KClass<INPUT>, specType: KClass<SPEC>): List<KotlinCodeGenerationProcessor<SELF, INPUT, SPEC>>
  fun <INPUT : Any> findProcessors(inputType: KClass<INPUT>): List<KotlinCodeGenerationProcessor<SELF, INPUT, *>>
  fun findProcessors(): List<KotlinCodeGenerationProcessor<SELF, *, *>>

}
