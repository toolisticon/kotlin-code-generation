package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinAnnotationClassSpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinAnnotationClassSpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinAnnotationClassSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  order = order,
  builderType = KotlinAnnotationClassSpecBuilder::class
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT?, builder: KotlinAnnotationClassSpecBuilder): KotlinAnnotationClassSpecBuilder
  override fun test(context: CONTEXT, input: Any?): Boolean = super.test(context, input)
}

