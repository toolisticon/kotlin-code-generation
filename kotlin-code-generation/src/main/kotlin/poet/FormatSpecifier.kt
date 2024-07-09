package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

object FormatSpecifier {
  const val STRING = "%S"
  const val STRING_TEMPLATE = "%P"
  const val TYPE = "%T"
  const val MEMBER = "%M"
  const val NAME = "%N"
  const val LITERAL = "%L"

  fun MemberName.asCodeBlock() = CodeBlock.of(MEMBER, this)
  fun String.asCodeBlock() = CodeBlock.of(STRING, this)
}
