package io.toolisticon.kotlin.generation.spi

import kotlin.reflect.KClass

interface KotlinCodeGenerationSpiRegistry {
  val contextTypeUpperBound: KClass<*>

  val strategies: List<KotlinCodeGenerationStrategy<*, *, *>>
  val processors: List<KotlinCodeGenerationProcessor<*, *, *>>

  fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> findStrategies(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
    specType: KClass<SPEC>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>>

  fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> findStrategies(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, *>>

  fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>> findStrategies(
    subcontextType: KClass<CONTEXT>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, *, *>>

  fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> findProcessors(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
    builderType: KClass<BUILDER>
  ): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>>

  fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> findProcessors(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
  ): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, *>>

  fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>> findProcessors(
    subcontextType: KClass<CONTEXT>
  ): List<KotlinCodeGenerationProcessor<CONTEXT, *, *>>
}


//inline fun <reified CONTEXT : KotlinCodeGenerationContext<CONTEXT>, reified INPUT : Any, reified SPEC : Any> KotlinCodeGenerationSpiRegistry.findStrategies(): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>> = this.findStrategies(CONTEXT::class, INPUT::class, SPEC::class)
//inline fun <reified CONTEXT : KotlinCodeGenerationContext<CONTEXT>, reified INPUT : Any> KotlinCodeGenerationSpiRegistry.findStrategies(): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, *>> = this.findStrategies(CONTEXT::class, INPUT::class)
//inline fun <reified CONTEXT : KotlinCodeGenerationContext<CONTEXT>> KotlinCodeGenerationSpiRegistry.findStrategies(): List<KotlinCodeGenerationStrategy<CONTEXT, *,*>> = this.findStrategies(CONTEXT::class)

//fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> findStrategies(
//  subcontextType: KClass<CONTEXT>,
//  inputType: KClass<INPUT>
//): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, *>>
//
//fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>> findStrategies(
//  subcontextType: KClass<CONTEXT>
//): List<KotlinCodeGenerationStrategy<CONTEXT, *, *>>
//
//fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> findProcessors(
//  subcontextType: KClass<CONTEXT>,
//  inputType: KClass<INPUT>,
//  builderType: KClass<BUILDER>
//): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>>
//
//fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> findProcessors(
//  subcontextType: KClass<CONTEXT>,
//  inputType: KClass<INPUT>,
//): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, *>>
//
//fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>> findProcessors(
//  subcontextType: KClass<CONTEXT>
//): List<KotlinCodeGenerationProcessor<CONTEXT, *, *>>

//
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> dataClassSpecProcessors(): DataClassSpecProcessorList<CONTEXT, INPUT> = DataClassSpecProcessorList.of(this)
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> enumClassSpecProcessors(): EnumClassSpecProcessorList<CONTEXT, INPUT> = EnumClassSpecProcessorList.of(this)
//
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> constructorParameterSpecProcessors(): ConstructorPropertySpecProcessorList<CONTEXT, INPUT> = ConstructorPropertySpecProcessorList.of(this)
//
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> fileSpecProcessors(): FileSpecProcessorList<CONTEXT, INPUT> = FileSpecProcessorList.of(this)
