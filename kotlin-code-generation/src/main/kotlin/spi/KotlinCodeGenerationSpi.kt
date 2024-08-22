package io.toolisticon.kotlin.generation.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import java.util.function.BiPredicate
import kotlin.reflect.KClass

/**
 * Root interface for code generation spi.
 *
 * There are two kinds of spi:
 *
 * * strategies
 * * processors
 */
@ExperimentalKotlinPoetApi
sealed interface KotlinCodeGenerationSpi<CONTEXT: KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> : Comparable<KotlinCodeGenerationSpi<*, *>>, BiPredicate<CONTEXT, Any?> {
  companion object {
    val metaInfServices = "META-INF/services/${KotlinCodeGenerationSpi::class.qualifiedName}"
    const val DEFAULT_ORDER = 0
  }

  /**
   * The name that is used when the implementing class is listed in a `META-INF/services/` file.
   * Attention: SPI uses java naming, so inner classes have to be separated by `$`.
   */
  val name: String

  /**
   * The type of the generic INPUT, used to filter relevant instances.
   */
  val inputType: KClass<out INPUT>

  /**
   * The type of the generic CONTEXT, used to filter relevant instances.
   */
  val contextType: KClass<out CONTEXT>

  /**
   * Order is used to sort spi instances.
   */
  val order: Int

  /**
   * Compare by order to sort list of SPI instances.
   *
   * @param other the other spi instance
   * @return -1,0,1 as specified by [Comparable]
   */
  override fun compareTo(other: KotlinCodeGenerationSpi<*, *>): Int = order.compareTo(other.order)

  /**
   * If `true`, the spi instance is executed, else ignored.
   *
   * @param context the context we are operating in
   * @param input the concrete work item, for the check this is unbound and nullable, so we can check against calling with unsupported types.
   * @return `true` when the spi shoud be applied.
   */
  override fun test(context: CONTEXT, input: Any?): Boolean = input == null || inputType == input::class
}
