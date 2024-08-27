package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

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
  fun invoke(context: CONTEXT, builder: KotlinFileSpecBuilder): KotlinFileSpecBuilder = invoke(context,null,builder)
  abstract override fun invoke(context: CONTEXT, input: INPUT?, builder: KotlinFileSpecBuilder): KotlinFileSpecBuilder
  override fun test(context: CONTEXT, input: Any?): Boolean = super.test(context, input)
}
