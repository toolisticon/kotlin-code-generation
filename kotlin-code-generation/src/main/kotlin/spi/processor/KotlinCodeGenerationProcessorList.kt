package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList


@ExperimentalKotlinPoetApi
@JvmInline
value class KotlinCodeGenerationProcessorList<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any>(
  private val value: List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>>
) : List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>> by value {

  companion object {
    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> of(spi: List<KotlinCodeGenerationSpi<*, *>>): KotlinCodeGenerationProcessorList<CONTEXT, INPUT, SPEC> {
      val value: List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, SPEC>> = spi.sorted()
        .filterIsInstance<KotlinCodeGenerationProcessor<CONTEXT, INPUT, SPEC>>()

      return KotlinCodeGenerationProcessorList(value)
    }
  }

  operator fun invoke(context: CONTEXT, input: INPUT?, builder: BUILDER): BUILDER = value.sorted()
    .filter { it.test(context, input) }
    .fold(builder) { acc, cur ->
      cur.invoke(context, input, acc)
    }
}
