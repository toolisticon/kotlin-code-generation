package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass

/**
 * Allows type safe access to all implementation instances of [KotlinCodeGenerationStrategy] and [KotlinCodeGenerationProcessor].
 */
@ExperimentalKotlinPoetApi
@Deprecated("remove")
abstract class AbstractKotlinCodeGenerationSpiRegistry(val contextType: KClass<*>) {

  abstract fun getStrategies(): Collection<KotlinCodeGenerationStrategy<*, *, *>>
  abstract fun getProcessors(): Collection<KotlinCodeGenerationProcessor<*, *, *>>
//
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> dataClassSpecProcessors(): DataClassSpecProcessorList<CONTEXT, INPUT> = DataClassSpecProcessorList.of(this)
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> enumClassSpecProcessors(): EnumClassSpecProcessorList<CONTEXT, INPUT> = EnumClassSpecProcessorList.of(this)
//
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> constructorParameterSpecProcessors(): ConstructorPropertySpecProcessorList<CONTEXT, INPUT> = ConstructorPropertySpecProcessorList.of(this)
//
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any> fileSpecProcessors(): FileSpecProcessorList<CONTEXT, INPUT> = FileSpecProcessorList.of(this)
//
//  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
//  inline fun <reified CONTEXT : KotlinCodeGenerationContext, reified INPUT : Any, reified SPEC : Any> getStrategy(): KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC> {
//    val strategies = findStrategies(CONTEXT::class, INPUT::class, SPEC::class)
//    requireNotNull(strategies.isNotEmpty()) { "No Strategy found for targetSpec=${SPEC::class}." }
//
//    return strategies.single()
//  }
//
//  abstract fun <SUBCONTEXT : KotlinCodeGenerationContext, INPUT : Any, SPEC : Any> findStrategies(
//    subcontextType: KClass<SUBCONTEXT>,
//    inputType: KClass<INPUT>,
//    specType: KClass<SPEC>
//  ): List<KotlinCodeGenerationStrategy<SUBCONTEXT, INPUT, SPEC>>
}
