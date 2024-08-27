package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinClassSpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinClassSpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinClassSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  order = order,
  builderType = KotlinClassSpecBuilder::class
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT?, builder: KotlinClassSpecBuilder): KotlinClassSpecBuilder
  override fun test(context: CONTEXT, input: Any?): Boolean = super.test(context, input)
}


