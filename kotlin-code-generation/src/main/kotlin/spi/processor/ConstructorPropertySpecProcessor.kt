package io.toolisticon.kotlin.generation.spi.processor

import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import kotlin.reflect.KClass

abstract class ConstructorPropertySpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : AbstractKotlinCodeGenerationProcessor<CONTEXT, INPUT, KotlinConstructorPropertySpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  order = order,
  builderType = KotlinConstructorPropertySpecBuilder::class
)


@JvmInline
value class ConstructorPropertySpecProcessorList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  private val list: List<ConstructorPropertySpecProcessor<CONTEXT, INPUT>>
) : List<ConstructorPropertySpecProcessor<CONTEXT, INPUT>> by list {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> of(registry: KotlinCodeGenerationSpiRegistry<CONTEXT>): ConstructorPropertySpecProcessorList<CONTEXT, INPUT> {
      return ConstructorPropertySpecProcessorList(registry.processors.values.filterIsInstance<ConstructorPropertySpecProcessor<CONTEXT, INPUT>>())
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
