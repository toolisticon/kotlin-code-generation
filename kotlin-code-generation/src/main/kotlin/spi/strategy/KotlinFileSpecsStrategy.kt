package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecs
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy]
 * that will generate multiple [io.toolisticon.kotlin.generation.spec.KotlinFileSpec]s wrapped in [KotlinFileSpecs].
 *
 * Implementations should override the `invoke` function to call multiple [KotlinFileSpecStrategy]s and collect the
 * results.
 */
@ExperimentalKotlinPoetApi
abstract class KotlinFileSpecsStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationStrategyBase<CONTEXT, INPUT, KotlinFileSpecs>(
  contextType = contextType,
  inputType = inputType,
  specType = KotlinFileSpecs::class,
  order = order
) {

  abstract override fun invoke(context: CONTEXT, input: INPUT): KotlinFileSpecs

  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)
}
