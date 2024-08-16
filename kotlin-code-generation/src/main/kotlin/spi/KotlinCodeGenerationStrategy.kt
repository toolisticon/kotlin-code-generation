package io.toolisticon.kotlin.generation.spi

import kotlin.reflect.KClass

/**
 * Root marker interface for all strategies.
 */
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

}
