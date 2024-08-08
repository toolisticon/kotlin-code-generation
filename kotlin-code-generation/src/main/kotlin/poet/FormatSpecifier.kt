package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format

object FormatSpecifier {
  const val STRING = format.FORMAT_STRING
  const val STRING_TEMPLATE = format.FORMAT_STRING_TEMPLATE
  const val TYPE = format.FORMAT_TYPE
  const val MEMBER = format.FORMAT_MEMBER
  const val NAME = format.FORMAT_NAME
  const val LITERAL = format.FORMAT_LITERAL

  fun MemberName.asCodeBlock() = CodeBlock.of(MEMBER, this)
  fun String.asCodeBlock() = CodeBlock.of(STRING, this)
}
