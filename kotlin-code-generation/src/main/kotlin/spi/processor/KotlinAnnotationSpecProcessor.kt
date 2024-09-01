package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinAnnotationSpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinAnnotationSpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinAnnotationSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  order = order,
  builderType = KotlinAnnotationSpecBuilder::class
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT, builder: KotlinAnnotationSpecBuilder): KotlinAnnotationSpecBuilder
  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)
}


