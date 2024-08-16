package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

@ExperimentalKotlinPoetApi
@JvmInline
value class ConstructorPropertySpecProcessorList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  private val list: List<ConstructorPropertySpecProcessor<CONTEXT, INPUT>>
) : List<ConstructorPropertySpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> of(registry: KotlinCodeGenerationSpiRegistry): ConstructorPropertySpecProcessorList<CONTEXT, INPUT> {
      return ConstructorPropertySpecProcessorList(registry.processors.filterIsInstance<ConstructorPropertySpecProcessor<CONTEXT, INPUT>>())
    }
  }

  /**
   * Execute all processors if predicate allows it.
   */
  operator fun invoke(
    ctx: CONTEXT,
    input: INPUT,
    builder: KotlinConstructorPropertySpecBuilder
  ) = filter { it.test(ctx, input) }.forEach {
    it.invoke(ctx, input, builder)
  }
}
