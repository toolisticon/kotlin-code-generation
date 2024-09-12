package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecIterable
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Base class for [KotlinFileSpecStrategy] and [KotlinFileSpecListStrategy] so we can join single and multi results
 * during generation.
 */
@ExperimentalKotlinPoetApi
sealed class KotlinFileSpecIterableStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : KotlinFileSpecIterable>(
  contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  specType: KClass<SPEC>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationStrategyBase<CONTEXT, INPUT, SPEC>(
  contextType = contextType,
  inputType = inputType,
  specType = specType,
  order = order
) {

  abstract override fun invoke(context: CONTEXT, input: INPUT): SPEC

  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)
}
