package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationStrategy
import kotlin.reflect.KClass

/**
 * Wraps list of [KotlinCodeGenerationStrategy] instances. Used to provide additional functionalities.
 */
@ExperimentalKotlinPoetApi
@JvmInline
value class KotlinCodeGenerationStrategyList(
  @PublishedApi
  internal val list: List<UnboundKotlinCodeGenerationStrategy>
) : List<UnboundKotlinCodeGenerationStrategy> by list {

  constructor(vararg strategy: UnboundKotlinCodeGenerationStrategy) : this(strategy.toList())

  /**
   * Filter the current list and return instances of given type.
   *
   * @param strategyType defining which concrete implementations to use
   * @return list containing only instances of given [strategyType]
   */
  fun <STRATEGY : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> filter(
    strategyType: KClass<STRATEGY>
  ): List<STRATEGY> {
    return list.filterIsInstance(strategyType.java)
  }

  /**
   * Filter the current list and return instances of given type.
   * @return list containing only instances of reified type.
   */
  inline fun <reified STRATEGY : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> filter() = filter(STRATEGY::class)

  override fun toString(): String = "KotlinCodeGenerationStrategyList(strategies=${list.map { it.name }})"
}

@ExperimentalKotlinPoetApi
fun <STRATEGY : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> List<STRATEGY>.executeSingle(
  context: CONTEXT,
  input: INPUT
): SPEC? = single().execute(context, input)

@ExperimentalKotlinPoetApi
fun <STRATEGY : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> List<STRATEGY>.executeAll(
  context: CONTEXT,
  input: INPUT
): List<SPEC> = mapNotNull { it.execute(context, input) }
