package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import kotlin.reflect.KClass

/**
 * Abstract base implementation of [KotlinCodeGenerationStrategy] for convenience setting of generic types.
 */
@ExperimentalKotlinPoetApi
abstract class KotlinCodeGenerationStrategyBase<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any>(
  override val contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  override val specType: KClass<SPEC>,
  override val order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC> {

  abstract override fun invoke(context: CONTEXT, input: INPUT): SPEC
  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)

  override val name: String by lazy { javaClass.name }
  override fun toString(): String = name
}
