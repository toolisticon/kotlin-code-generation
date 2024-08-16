package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

@ExperimentalKotlinPoetApi
@JvmInline
value class DataClassSpecProcessorList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  private val list: List<DataClassSpecProcessor<CONTEXT, INPUT>>
) : List<DataClassSpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> of(registry: KotlinCodeGenerationSpiRegistry): DataClassSpecProcessorList<CONTEXT, INPUT> {
      return DataClassSpecProcessorList(registry.processors.filterIsInstance<DataClassSpecProcessor<CONTEXT, INPUT>>())
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
