package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationProcessor
import kotlin.reflect.KClass

/**
 * Wraps list of [KotlinCodeGenerationProcessor] instances. Used to provide additional functionalities.
 */
@ExperimentalKotlinPoetApi
@JvmInline
value class KotlinCodeGenerationProcessorList(
  @PublishedApi
  internal val list: List<UnboundKotlinCodeGenerationProcessor>
) : List<UnboundKotlinCodeGenerationProcessor> by list {

  constructor(vararg processors: UnboundKotlinCodeGenerationProcessor) : this(processors.toList())

  /**
   * Filter the current list and return instances of given type.
   *
   * @param processorType defining which concrete implementations to use
   * @return list containing only instances of given [processorType]
   */
  fun <PROCESSOR : KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> filter(
    processorType: KClass<PROCESSOR>
  ): List<PROCESSOR> {
    return list.filterIsInstance(processorType.java)
  }

  /**
   * Filter the current list and return instances of given type.
   * @return list containing only instances of reified type.
   */
  inline fun <reified PROCESSOR : KotlinCodeGenerationProcessor<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> filter() = filter(PROCESSOR::class)


  override fun toString(): String = "KotlinCodeGenerationProcessorList(processors=${list.map { it.name }})"
}


@ExperimentalKotlinPoetApi
fun <PROCESSOR : KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> List<PROCESSOR>.executeSingle(
  context: CONTEXT,
  input: INPUT,
  builder: BUILDER
): BUILDER = single().execute(context, input, builder)

@ExperimentalKotlinPoetApi
fun <PROCESSOR : KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> List<PROCESSOR>.executeAll(
  context: CONTEXT,
  input: INPUT,
  builder: BUILDER
): BUILDER = fold(builder) { acc, cur -> cur.execute(context, input, acc) }
