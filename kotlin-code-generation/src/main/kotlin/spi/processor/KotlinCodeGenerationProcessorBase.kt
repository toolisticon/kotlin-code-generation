package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

/**
 * Abstract base implementation of [KotlinCodeGenerationProcessor] for convenience setting of generic types.
 */
@ExperimentalKotlinPoetApi
abstract class KotlinCodeGenerationProcessorBase<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any>(
  override val contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  override val builderType: KClass<BUILDER>,
  override val order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER> {

  abstract override fun invoke(context: CONTEXT, input: INPUT, builder: BUILDER): BUILDER
  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)

  override val name: String by lazy { this.javaClass.name }
  override fun toString(): String = name
}
