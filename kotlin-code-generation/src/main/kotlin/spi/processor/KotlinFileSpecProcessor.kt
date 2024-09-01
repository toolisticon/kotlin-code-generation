package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import io.toolisticon.kotlin.generation.spi.EmptyInput
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinFileSpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinFileSpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinFileSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  builderType = KotlinFileSpecBuilder::class,
  order = order
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT, builder: KotlinFileSpecBuilder): KotlinFileSpecBuilder
  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)
}

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinFileSpecBuilder] but do not work on a loop variable.
 */
@ExperimentalKotlinPoetApi
abstract class KotlinFileSpecEmptyInputProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>>(
  contextType: KClass<CONTEXT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, EmptyInput, KotlinFileSpecBuilder>(
  contextType = contextType,
  inputType = EmptyInput::class,
  builderType = KotlinFileSpecBuilder::class,
  order = order
) {
  /**
   * Implement this to avoid checks for non-null input.
   */
  abstract fun invoke(context: CONTEXT, builder: KotlinFileSpecBuilder): KotlinFileSpecBuilder

  override fun invoke(context: CONTEXT, input: EmptyInput, builder: KotlinFileSpecBuilder): KotlinFileSpecBuilder = invoke(context, builder)

  /**
   * Implement this to check only based on context.
   */
  fun test(context: CONTEXT): Boolean = context::class.isSubclassOf(contextType)

  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input) && test(context)
}
