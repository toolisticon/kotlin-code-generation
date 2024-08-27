package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinFunSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinFunSpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinFunSpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinFunSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  builderType = KotlinFunSpecBuilder::class,
  order = order
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT?, builder: KotlinFunSpecBuilder): KotlinFunSpecBuilder
  override fun test(context: CONTEXT, input: Any?): Boolean = super.test(context, input)
}



