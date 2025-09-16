package io.toolisticon.kotlin.generation.support

import org.apiguardian.api.API
import org.apiguardian.api.API.Status.EXPERIMENTAL
import kotlin.text.replace
import kotlin.text.split

/**
 * A transformation function for strings, which can be used to apply various transformations to a string.
 */
@API(status = EXPERIMENTAL, since = "2025.7.0")
fun interface StringTransformation : (String) -> String

@Suppress("ClassName", "FunctionName")
data object StringTransformations {
  fun String.transform(f: StringTransformation) = f(this)

  val EMPTY = emptyList<StringTransformation>()

  @JvmInline
  value class CHAIN(val list: List<StringTransformation>) : StringTransformation {
    override fun invoke(input: String): String = list.fold(input) { acc, cur -> cur(acc) }
  }

  data object NOOP : StringTransformation {
    override fun invoke(input: String): String = input
  }

  class WRAP(private val wrap: String = "'") : StringTransformation {
    override fun invoke(input: String): String = if (wrap.isEmpty()) NOOP(input) else "$wrap$input$wrap"
    override fun toString(): String = "WRAP(wrap='$wrap')"
  }

  fun SHORTEN(maxLength: Int?, append: String = "...") =
    if (maxLength == null || maxLength == Int.MAX_VALUE) NOOP else stringTransformation("shorten=$maxLength") {
      if (maxLength != null && it.length > maxLength)
        it.take(maxLength) + append
      else it
    }

  /**
   * Converts a string to UpperSnakeCase, which is a common naming convention
   * for static constants in programming.
   *
   * Example: `fooBarHelloWorld` becomes `FOO_BAR_HELLO_WORLD`.
   */
  data object TO_UPPER_SNAKE_CASE : StringTransformation {
    override fun invoke(input: String): String {
      val normalized = input
        .trim()
        // Replace any sequence of non-alphanumeric characters (including whitespace) with underscore
        .replace(Regex("[^A-Za-z0-9]+"), "_")
        // Collapse multiple underscores
        .replace(Regex("_+"), "_")
        // Remove leading/trailing underscores
        .trim('_')

      if (normalized.isEmpty()) return ""

      fun Char.isLowerOrDigit() = this.isLowerCase() || this.isDigit()

      val words = mutableListOf<String>()
      normalized.split("_").forEach { token ->
        if (token.isEmpty()) return@forEach
        val sb = StringBuilder()
        var lowerCountSinceBoundary = 0
        for (i in token.indices) {
          val c = token[i]
          val prev = if (i > 0) token[i - 1] else null
          val next = if (i + 1 < token.length) token[i + 1] else null

          val shouldSplitHere = prev != null &&
            prev.isLowerOrDigit() && c.isUpperCase() &&
            (next != null && next.isLowerCase()) &&
            lowerCountSinceBoundary >= 2

          if (shouldSplitHere) {
            words += sb.toString()
            sb.setLength(0)
            lowerCountSinceBoundary = 0
          }

          sb.append(c)
          if (c.isLowerOrDigit()) lowerCountSinceBoundary++
        }
        if (sb.isNotEmpty()) words += sb.toString()
      }

      return words.joinToString("_") { it.uppercase() }
    }
  }

  /**
   * Converts a string to UpperCamelCase, which is a common naming convention
   * for TypeNames in programming.
   *
   * Example: `foo_bar_hello_world` becomes `FooBarHelloWorld`.
   */
  data object TO_UPPER_CAMEL_CASE : StringTransformation {
    override fun invoke(input: String): String = input.split(Regex("_+|\\s+|(?<=[a-z])(?=[A-Z])"))
      .filter { it.isNotEmpty() }
      .joinToString("") { part ->
        part.lowercase().replaceFirstChar { it.uppercase() }
      }
  }

  data object TO_LOWER_CAMEL_CASE : StringTransformation {
    override fun invoke(input: String): String = TO_UPPER_CAMEL_CASE(input).replaceFirstChar { it.lowercase() }
  }

  /**
   * Removes spaces and converts to lower case.
   *
   * Example: 'Add Customer` becomes `addcustomer`
   */
  data object WITHOUT_SPACES_TO_LOWER : StringTransformation {
    override fun invoke(input: String): String = input.replace(" ", "").lowercase()
  }

  private fun stringTransformation(toString: String, inner: StringTransformation) = object : StringTransformation {
    override fun invoke(source: String) = inner(source)
    override fun toString() = "StringTransformation($toString)"
  }
}
