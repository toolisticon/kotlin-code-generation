package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Root interface of all processors. Used to load all implementations
 * via ServiceLoader/SPI.
 */
@ExperimentalKotlinPoetApi
interface KotlinCodeGenerationProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> : KotlinCodeGenerationSpi<CONTEXT, INPUT> {

  override val contextType: KClass<CONTEXT>
  override val inputType: KClass<INPUT>
  override val order: Int
  override val name: String
  val builderType: KClass<BUILDER>

  operator fun invoke(context: CONTEXT, input: INPUT?, builder: BUILDER): BUILDER
}

@ExperimentalKotlinPoetApi
fun KotlinCodeGenerationProcessor<*, *, *>.matchesContextType(contextType: KClass<*>) = this.contextType.isSubclassOf(contextType)

@ExperimentalKotlinPoetApi
fun KotlinCodeGenerationProcessor<*, *, *>.matchesInputType(inputType: KClass<*>) = this.inputType.isSubclassOf(inputType)

@ExperimentalKotlinPoetApi
fun KotlinCodeGenerationProcessor<*, *, *>.matchesBuilderType(builderType: KClass<*>) = this.builderType.isSubclassOf(builderType)

