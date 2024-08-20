package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass

/**
 * Root interface of all processors. Used to load all implementations
 * via ServiceLoader/SPI.
 */
@ExperimentalKotlinPoetApi
interface KotlinCodeGenerationProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> : KotlinCodeGenerationSpi<CONTEXT, INPUT> {

  override val contextType: KClass<CONTEXT>
  override val inputType: KClass<INPUT>
  override val order: Int
  override val name: String
  val builderType: KClass<BUILDER>

  /**
   * Input is nullable because we could use processors solely based on context.
   */
  operator fun invoke(context: CONTEXT, input: INPUT?, builder: BUILDER): BUILDER

  /**
   * Checks if this strategy should be applied (using `test`) and then runs `invoke`.
   */
  fun execute(context: CONTEXT, input: INPUT?, builder: BUILDER): BUILDER = if (test(context, input)) {
    invoke(context, input, builder)
  } else {
    builder
  }
}

@ExperimentalKotlinPoetApi
typealias UnboundKotlinCodeGenerationProcessor = KotlinCodeGenerationProcessor<*, *, *>
