package io.toolisticon.kotlin.generation.test.model

/**
 * Encapsulates a compiler error message.
 */
data class KotlinCompilationError(
  val message: String,
  val file: String
)
