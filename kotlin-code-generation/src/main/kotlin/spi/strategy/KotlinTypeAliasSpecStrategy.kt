package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy]
 * that will generate a [KotlinTypeAliasSpec].
 */
@ExperimentalKotlinPoetApi
abstract class KotlinTypeAliasSpecStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationStrategyBase<CONTEXT, INPUT, KotlinTypeAliasSpec>(
  contextType = contextType,
  inputType = inputType,
  specType = KotlinTypeAliasSpec::class,
  order = order
) {

  abstract override fun invoke(context: CONTEXT, input: INPUT): KotlinTypeAliasSpec

  override fun test(context: CONTEXT, input: Any?): Boolean = super.test(context, input)
}