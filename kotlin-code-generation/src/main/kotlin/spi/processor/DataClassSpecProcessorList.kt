package io.toolisticon.kotlin.generation.spi.processor

import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.AbstractKotlinCodeGenerationSpiRegistry

@JvmInline
value class DataClassSpecProcessorList<CONTEXT : KotlinCodeGenerationContext, INPUT : Any>(
  private val list: List<DataClassSpecProcessor<CONTEXT, INPUT>>
) : List<DataClassSpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any> of(registry: AbstractKotlinCodeGenerationSpiRegistry): DataClassSpecProcessorList<CONTEXT, INPUT> {
      return DataClassSpecProcessorList(registry.getProcessors().filterIsInstance<DataClassSpecProcessor<CONTEXT, INPUT>>())
    }
  }

  /**
   * Execute all processors if predicate allows it.
   */
  operator fun invoke(
    ctx: CONTEXT,
    input: INPUT,
    builder: KotlinDataClassSpecBuilder
  ) = filter { it.test(ctx, input) }.forEach {
    it.invoke(ctx, input, builder)
  }
}
