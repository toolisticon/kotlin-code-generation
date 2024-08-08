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
sealed interface KotlinCodeGenerationSpi<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> : Comparable<KotlinCodeGenerationSpi<CONTEXT, *>>, BiPredicate<CONTEXT, Any?> {
  companion object {
    const val DEFAULT_ORDER = 0
  }

  val name: String

  /**
   * The type of the generic INPUT, used to filter relevant instances.
   */
  val inputType: KClass<INPUT>

  /**
   * The type of the generic CONTEXT, used to filter relevant instances.
   */
  val contextType: KClass<CONTEXT>

  /**
   * Order is used to sort spi instances.
   */
  val order: Int

  /**
   * Compare by order to sort list of SPI instances.
   */
  override fun compareTo(other: KotlinCodeGenerationSpi<CONTEXT, *>): Int = order.compareTo(other.order)

  /**
   * If `true`, the spi instance is executed, else ignored.
   */
  override fun test(ctx: CONTEXT, input: Any?): Boolean = input == null || inputType == input::class
}

/**
 * Context used for SPI execution. Typically, holds mutable state that is modified while processing the chain.
 * Required because all spi-instances have to be generated via default-constructor, so all state data
 * we would normally store in a property has to be passed to every function call.
 */
interface KotlinCodeGenerationContext<SELF : KotlinCodeGenerationContext<SELF>> {

  /**
   * Include the [KotlinCodeGenerationSpiRegistry] so strategies and processors can access themselves if required.
   */
  val registry: KotlinCodeGenerationSpiRegistry<SELF>

}

abstract class AbstractKotlinCodeGenerationContext<SELF : KotlinCodeGenerationContext<SELF>>(
  override val registry: KotlinCodeGenerationSpiRegistry<SELF>
) : KotlinCodeGenerationContext<SELF>
