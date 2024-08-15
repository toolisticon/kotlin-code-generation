package io.toolisticon.kotlin.generation.spi

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
sealed interface KotlinCodeGenerationSpi<CONTEXT: KotlinCodeGenerationContext, INPUT : Any> : Comparable<KotlinCodeGenerationSpi<*, *>>, BiPredicate<CONTEXT, Any?> {
  companion object {
    val metaInfServices = "META-INF/services/${KotlinCodeGenerationSpi::class.qualifiedName}"
    const val DEFAULT_ORDER = 0
  }

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
   */
  override fun compareTo(other: KotlinCodeGenerationSpi<*, *>): Int = order.compareTo(other.order)

  /**
   * If `true`, the spi instance is executed, else ignored.
   */
  override fun test(ctx: CONTEXT, input: Any?): Boolean = input == null || inputType == input::class
}
