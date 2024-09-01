package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy]
 * that will generate a [KotlinValueClassSpec].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinValueClassSpecStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationStrategyBase<CONTEXT, INPUT, KotlinValueClassSpec>(
  contextType = contextType,
  inputType = inputType,
  specType = KotlinValueClassSpec::class,
  order = order
) {
  abstract override fun invoke(context: CONTEXT, input: INPUT): KotlinValueClassSpec

  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)
}
