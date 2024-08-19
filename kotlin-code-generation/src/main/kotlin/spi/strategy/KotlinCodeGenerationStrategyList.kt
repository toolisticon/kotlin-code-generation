package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.matchesContextType
import io.toolisticon.kotlin.generation.spi.matchesInputType
import io.toolisticon.kotlin.generation.spi.matchesSpecType
import kotlin.reflect.KClass


@ExperimentalKotlinPoetApi
@JvmInline
value class KotlinCodeGenerationStrategyList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any>(
  private val value: List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>>
) : List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>> by value {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> of(spi: List<KotlinCodeGenerationSpi<*, *>>): KotlinCodeGenerationStrategyList<CONTEXT, INPUT, SPEC> {
      val value: List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>> = spi.sorted()
        .filterIsInstance<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>>()

      return KotlinCodeGenerationStrategyList(value)
    }
  }

  @Suppress("UNCHECKED_CAST")
  fun <FILTER_CONTEXT : KotlinCodeGenerationContext<FILTER_CONTEXT>, FILTER_INPUT : Any, FILTER_SPEC : Any> filter(
    subcontextType: KClass<FILTER_CONTEXT>,
    inputType: KClass<FILTER_INPUT>,
    specType: KClass<FILTER_SPEC>
  ): KotlinCodeGenerationStrategyList<FILTER_CONTEXT, FILTER_INPUT, FILTER_SPEC> {
    return KotlinCodeGenerationStrategyList<FILTER_CONTEXT, FILTER_INPUT, FILTER_SPEC>(
      value = value.filter { it.matchesContextType(subcontextType) }
        .filter { it.matchesInputType(inputType) }
        .filter { it.matchesSpecType(specType) }
        .map { it as KotlinCodeGenerationStrategy<FILTER_CONTEXT, FILTER_INPUT, FILTER_SPEC> }
    )
  }

  @Suppress("UNCHECKED_CAST")
  fun <FILTER_CONTEXT : KotlinCodeGenerationContext<FILTER_CONTEXT>, FILTER_INPUT : Any> filter(
    subcontextType: KClass<FILTER_CONTEXT>,
    inputType: KClass<FILTER_INPUT>
  ): KotlinCodeGenerationStrategyList<FILTER_CONTEXT, FILTER_INPUT, *> {
    return KotlinCodeGenerationStrategyList<FILTER_CONTEXT, FILTER_INPUT, Any>(
      value = value.filter { it.matchesContextType(subcontextType) }
        .filter { it.matchesInputType(inputType) }
        .map { it as KotlinCodeGenerationStrategy<FILTER_CONTEXT, FILTER_INPUT, Any> }
    )
  }

  @Suppress("UNCHECKED_CAST")
  fun <FILTER_CONTEXT : KotlinCodeGenerationContext<FILTER_CONTEXT>> filter(
    subcontextType: KClass<FILTER_CONTEXT>
  ): KotlinCodeGenerationStrategyList<FILTER_CONTEXT, *, *> {
    return KotlinCodeGenerationStrategyList<FILTER_CONTEXT, Any, Any>(
      value = value.filter { it.matchesContextType(subcontextType) }
        .map { it as KotlinCodeGenerationStrategy<FILTER_CONTEXT, Any, Any> }
    )
  }

  operator fun invoke(context: CONTEXT, input: INPUT): List<SPEC> = value.sorted()
    .filter { it.test(context, input) }
    .map { it.invoke(context, input) }

}
