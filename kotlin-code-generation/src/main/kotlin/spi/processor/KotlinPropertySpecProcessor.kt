package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinPropertySpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinPropertySpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinPropertySpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinPropertySpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  builderType = KotlinPropertySpecBuilder::class,
  order = order
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT?, builder: KotlinPropertySpecBuilder): KotlinPropertySpecBuilder
  override fun test(context: CONTEXT, input: Any?): Boolean = super.test(context, input)
}



