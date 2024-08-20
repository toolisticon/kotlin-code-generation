package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass

/**
 * Root marker interface for all strategies.
 */
@ExperimentalKotlinPoetApi
interface KotlinCodeGenerationStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> : KotlinCodeGenerationSpi<CONTEXT, INPUT> {

  override val contextType: KClass<CONTEXT>
  override val inputType: KClass<INPUT>
  override val name: String
  override val order: Int

  /**
   * The type of the created SPEC, used to filter relevant instances for execution.
   */
  val specType: KClass<SPEC>

  operator fun invoke(context: CONTEXT, input: INPUT): SPEC

  /**
   * Checks if this strategy should be applied (using `test`) and then runs `invoke`.
   */
  fun execute(context: CONTEXT, input: INPUT): SPEC? = if (test(context, input)) {
    invoke(context, input)
  } else {
    null
  }
}

@ExperimentalKotlinPoetApi
typealias UnboundKotlinCodeGenerationStrategy = KotlinCodeGenerationStrategy<*, *, *>
