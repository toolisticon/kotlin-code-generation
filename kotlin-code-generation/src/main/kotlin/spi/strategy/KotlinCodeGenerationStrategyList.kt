package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationStrategy
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
@JvmInline
value class KotlinCodeGenerationStrategyList(val list: List<UnboundKotlinCodeGenerationStrategy>) :
    List<UnboundKotlinCodeGenerationStrategy> by list {

    constructor(vararg strategy: UnboundKotlinCodeGenerationStrategy) : this(strategy.toList())

    fun <STRATEGY : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>, CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> filter(
        strategyType: KClass<STRATEGY>
    ): List<STRATEGY> {
        return list.filterIsInstance(strategyType.java)
    }
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
): List<SPEC> = map {
    it.execute(context, input)
}.filterNotNull()
