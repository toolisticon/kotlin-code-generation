package io.toolisticon.kotlin.generation.spi

/**
 * Root interface for code generation spi.
 *
 * There are two kinds of spi:
 *
 * * stragegies
 * * propcessors
 */
sealed interface KotlinCodeGenerationSpi {
  companion object Order {
    const val DEFAULT_ORDER = 0
  }

  val order: Int
}
