package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_KCLASS
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_LITERAL
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_MEMBER
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asMemberName
import kotlin.reflect.KClass

/**
 * Allows concatenating and wrapping of multiple codeBlocks or format/value pairs.
 */
@ExperimentalKotlinPoetApi
data class CodeBlockArray<T>(
  val format: Format,
  val items: Collection<T> = emptyList()
) : Builder<CodeBlock> {
  companion object {

    /**
     * Holds formatting values as kotlin poet format, pre- and suffix, separator.
     */
    data class Format(val format: String, val separator: CharSequence = ", ", val prefix: CharSequence = "[", val suffix: CharSequence = "]")

    /**
     * Special format for concatenating codeBlocks.
     */
    val CODE_BLOCK_FORMAT = CodeBlockArray.Companion.Format(format = "", prefix = "", suffix = "")

    fun codeBlockArray(format: Format = CODE_BLOCK_FORMAT, vararg items: CodeBlock) = CodeBlockArray<CodeBlock>(format, items.toList())

    fun stringArray(vararg items: String) = CodeBlockArray(FORMAT_STRING, items.toList())

    fun enumArray(vararg items: Enum<*>): CodeBlockArray<MemberName> = CodeBlockArray(FORMAT_MEMBER, items.map { it.asMemberName() }.toList())

    fun kclassArray(vararg items: KClass<*>): CodeBlockArray<KClass<*>> = CodeBlockArray(FORMAT_KCLASS, items.toList())

    fun typeNameArray(vararg items: TypeName): CodeBlockArray<TypeName> = CodeBlockArray(FORMAT_KCLASS, items.toList())

    fun numberArray(vararg items: Number): CodeBlockArray<Number> = CodeBlockArray(FORMAT_LITERAL, items.toList())
  }

  constructor(
    format: String,
    items: Collection<T> = emptyList(),
    separator: CharSequence = ", ",
    prefix: CharSequence = "[",
    suffix: CharSequence = "]",
  ) : this(format = Format(format = format, separator = separator, prefix = prefix, suffix = suffix), items = items)

  constructor(format: String, item: T) : this(format, listOf(item))

  operator fun plus(item: T): CodeBlockArray<T> = copy(items = items + item)

  override fun build(): CodeBlock = items.map {
    if (it is CodeBlock) {
      it
    } else {
      buildCodeBlock(format.format, it)
    }
  }.joinToCode(prefix = format.prefix, suffix = format.suffix)
}
