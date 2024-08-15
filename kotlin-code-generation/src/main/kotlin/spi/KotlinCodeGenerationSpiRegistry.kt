package io.toolisticon.kotlin.generation.spi

import kotlin.reflect.KClass

interface KotlinCodeGenerationSpiRegistry {
  val contextType: KClass<out KotlinCodeGenerationContext>

  val strategies: List<KotlinCodeGenerationStrategy<*, *, *>>
  val processors: List<KotlinCodeGenerationProcessor<*, *, *>>

  fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any, SPEC : Any> findStrategies(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
    specType: KClass<SPEC>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>>

  fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any> findStrategies(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, *>>

  fun <CONTEXT : KotlinCodeGenerationContext> findStrategies(
    subcontextType: KClass<CONTEXT>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, *, *>>

  fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any, BUILDER : Any> findProcessors(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
    builderType: KClass<BUILDER>
  ): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>>

  fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any> findProcessors(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
  ): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, *>>

  fun <CONTEXT : KotlinCodeGenerationContext> findProcessors(
    subcontextType: KClass<CONTEXT>
  ): List<KotlinCodeGenerationProcessor<CONTEXT, *, *>>


}
