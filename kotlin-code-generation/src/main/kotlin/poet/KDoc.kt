package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.CodeBlock
import java.util.function.Supplier

/**
 * For all [com.squareup.kotlinpoet.Documentable] types, wrap the [CodeBlock] in [KDoc].
 */
@JvmInline
value class KDoc(private val value: CodeBlock) : Supplier<CodeBlock> {
  companion object {

    fun of(doc: String) = of(FormatSpecifier.LITERAL, doc)

    fun of(format: String, first: Any, vararg other: Any) = KDoc(
      value = CodeBlock.of(
        format = format,
        args = (listOf(first, *other).toTypedArray())
      )
    )
  }

  override fun get(): CodeBlock = value
  override fun toString() = get().toString()
}
