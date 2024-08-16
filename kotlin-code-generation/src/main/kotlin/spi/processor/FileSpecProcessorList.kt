package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry

@ExperimentalKotlinPoetApi
@JvmInline
value class FileSpecProcessorList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  private val list: List<FileSpecProcessor<CONTEXT, INPUT>>
) : List<FileSpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> of(registry: KotlinCodeGenerationSpiRegistry): FileSpecProcessorList<CONTEXT, INPUT> {
      return FileSpecProcessorList(registry.processors.filterIsInstance<FileSpecProcessor<CONTEXT, INPUT>>())
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
