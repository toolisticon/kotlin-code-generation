package io.toolisticon.kotlin.generation.spi.processor

import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.AbstractKotlinCodeGenerationSpiRegistry

@JvmInline
value class FileSpecProcessorList<CONTEXT : KotlinCodeGenerationContext, INPUT : Any>(
  private val list: List<FileSpecProcessor<CONTEXT, INPUT>>
) : List<FileSpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any> of(registry: AbstractKotlinCodeGenerationSpiRegistry): FileSpecProcessorList<CONTEXT, INPUT> {
      return FileSpecProcessorList(registry.getProcessors().filterIsInstance<FileSpecProcessor<CONTEXT, INPUT>>())
    }
  }

  /**
   * Execute all processors if predicate allows it.
   */
  operator fun invoke(
    ctx: CONTEXT,
    input: INPUT,
    builder: KotlinFileSpecBuilder
  ) = filter { it.test(ctx, input) }.forEach {
    it.invoke(ctx, input, builder)
  }
}
