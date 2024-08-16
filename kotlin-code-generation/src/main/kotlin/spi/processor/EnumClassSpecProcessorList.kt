package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinEnumClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

@ExperimentalKotlinPoetApi
@JvmInline
value class EnumClassSpecProcessorList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  private val list: List<EnumClassSpecProcessor<CONTEXT, INPUT>>
) : List<EnumClassSpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> of(registry: KotlinCodeGenerationSpiRegistry): EnumClassSpecProcessorList<CONTEXT, INPUT> {
      return EnumClassSpecProcessorList(registry.processors.filterIsInstance<EnumClassSpecProcessor<CONTEXT, INPUT>>())
    }
  }

  /**
   * Execute all processors if predicate allows it.
   */
  operator fun invoke(
    ctx: CONTEXT,
    input: INPUT,
    builder: KotlinEnumClassSpecBuilder
  ) = filter { it.test(ctx, input) }.forEach {
    it.invoke(ctx, input, builder)
  }
}
