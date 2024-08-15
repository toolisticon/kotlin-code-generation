package io.toolisticon.kotlin.generation.spi.processor

import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.AbstractKotlinCodeGenerationSpiRegistry

@JvmInline
value class ConstructorPropertySpecProcessorList<CONTEXT : KotlinCodeGenerationContext, INPUT : Any>(
  private val list: List<ConstructorPropertySpecProcessor<CONTEXT, INPUT>>
) : List<ConstructorPropertySpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any> of(registry: AbstractKotlinCodeGenerationSpiRegistry): ConstructorPropertySpecProcessorList<CONTEXT, INPUT> {
      return ConstructorPropertySpecProcessorList(registry.getProcessors().filterIsInstance<ConstructorPropertySpecProcessor<CONTEXT, INPUT>>())
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
