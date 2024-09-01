package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinTypeAliasSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor]
 * that will visit/modify a [KotlinTypeAliasSpecBuilder].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinTypeAliasSpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : KotlinCodeGenerationProcessorBase<CONTEXT, INPUT, KotlinTypeAliasSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  builderType = KotlinTypeAliasSpecBuilder::class,
  order = order
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT, builder: KotlinTypeAliasSpecBuilder): KotlinTypeAliasSpecBuilder
  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)
}



