package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import kotlin.reflect.KClass

/**
 * Root interface of all processors. Used to load all implementations via ServiceLoader/SPI.
 *
 * A processor implements the visitor pattern. It takes the current context and the concrete work item to modify
 * the passed builder.
 *
 * Example: add additional annotations to a class builder.
 *
 * Hint: for implementing a concrete processor, use the [io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorBase].
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
  operator fun invoke(context: CONTEXT, input: INPUT, builder: BUILDER): BUILDER
  override fun test(context: CONTEXT, input: Any): Boolean = super.test(context, input)

  /**
   * Checks if this strategy should be applied (using `test`) and then runs `invoke`.
   *
   * @see [invoke] the executed processor logic
   * @see [test] to check if the processor should be applied.
   *
   * @param context the context we are operating in
   * @param input the concrete work item, nullable for [test].
   * @param builder the builder to modify
   * @return the passed builder instance for fluent usage
   */
  fun execute(context: CONTEXT, input: INPUT, builder: BUILDER): BUILDER = if (test(context, input)) {
    invoke(context, input, builder)
  } else {
    builder
  }
}

/**
 * Convenience alias to reference unbound processors without repeating the `<*,*,*>`.
 */
@ExperimentalKotlinPoetApi
typealias UnboundKotlinCodeGenerationProcessor = KotlinCodeGenerationProcessor<*, *, *>
