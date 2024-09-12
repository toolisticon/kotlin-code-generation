package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Used to implement a [io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy]
 * that will generate multiple [io.toolisticon.kotlin.generation.spec.KotlinFileSpec]s wrapped in [KotlinFileSpecList].
 *
 * Implementations should override the `invoke` function to call multiple [KotlinFileSpecStrategy]s and collect the
 * results.
 */
@ExperimentalKotlinPoetApi
abstract class KotlinFileSpecListStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinFileSpecIterableStrategy<CONTEXT, INPUT, KotlinFileSpecList>(
  contextType = contextType,
  inputType = inputType,
  specType = KotlinFileSpecList::class,
  order = order
) {

  abstract override fun invoke(context: CONTEXT, input: INPUT): KotlinFileSpecList

  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)

  override fun execute(context: CONTEXT, input: INPUT): KotlinFileSpecList = super.execute(context, input) ?: KotlinFileSpecList.EMPTY
}
