package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

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
}

@ExperimentalKotlinPoetApi
fun KotlinCodeGenerationStrategy<*, *, *>.matchesContextType(contextType: KClass<*>) = this.contextType.isSubclassOf(contextType)

@ExperimentalKotlinPoetApi
fun KotlinCodeGenerationStrategy<*, *, *>.matchesInputType(inputType: KClass<*>) = this.inputType.isSubclassOf(inputType)

@ExperimentalKotlinPoetApi
fun KotlinCodeGenerationStrategy<*, *, *>.matchesSpecType(specType: KClass<*>) = this.specType.isSubclassOf(specType)
