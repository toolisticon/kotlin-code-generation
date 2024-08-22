package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinConstructorPropertySpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinConstructorPropertySpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinConstructorPropertySpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  order = order,
  builderType = KotlinConstructorPropertySpecBuilder::class
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT?, builder: KotlinConstructorPropertySpecBuilder): KotlinConstructorPropertySpecBuilder
  override fun test(context: CONTEXT, input: Any?): Boolean = super.test(context, input)
}


