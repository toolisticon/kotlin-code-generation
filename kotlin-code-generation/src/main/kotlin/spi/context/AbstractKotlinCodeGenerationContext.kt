package io.toolisticon.kotlin.generation.spi.context

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
abstract class AbstractKotlinCodeGenerationContext<SELF : KotlinCodeGenerationContext<SELF>>(
  override val registry: KotlinCodeGenerationSpiRegistry
) : KotlinCodeGenerationContext<SELF> {
  abstract override val contextType: KClass<SELF>

  override fun findProcessors(): List<KotlinCodeGenerationProcessor<SELF, *, *>> = registry.findProcessors(contextType)
  override fun <INPUT : Any> findProcessors(inputType: KClass<INPUT>): List<KotlinCodeGenerationProcessor<SELF, INPUT, *>> = registry.findProcessors(contextType, inputType)
  override fun <INPUT : Any, SPEC : Any> findProcessors(inputType: KClass<INPUT>, specType: KClass<SPEC>): List<KotlinCodeGenerationProcessor<SELF, INPUT, SPEC>> =
    registry.findProcessors(contextType, inputType, specType)

  override fun findStrategies(): List<KotlinCodeGenerationStrategy<SELF, *, *>> = registry.findStrategies(contextType)
  override fun <INPUT : Any> findStrategies(inputType: KClass<INPUT>): List<KotlinCodeGenerationStrategy<SELF, INPUT, *>> = registry.findStrategies(contextType, inputType)
  override fun <INPUT : Any, SPEC : Any> findStrategies(inputType: KClass<INPUT>, specType: KClass<SPEC>): List<KotlinCodeGenerationStrategy<SELF, INPUT, SPEC>> =
    registry.findStrategies(contextType, inputType, specType)


}
