package io.toolisticon.kotlin.generation.spi

import kotlin.reflect.KClass

/**
 * Root interface of all processors. Used to load all implementations
 * via ServiceLoader/SPI.
 */
interface KotlinCodeGenerationProcessor<CONTEXT : KotlinCodeGenerationContext, INPUT : Any, BUILDER : Any> : KotlinCodeGenerationSpi<CONTEXT, INPUT> {

  override val contextType: KClass<CONTEXT>
  override val inputType: KClass<INPUT>
  override val order: Int
  override val name: String
  val builderType: KClass<BUILDER>

  operator fun invoke(context: CONTEXT, input: INPUT?, builder: BUILDER): BUILDER
}
