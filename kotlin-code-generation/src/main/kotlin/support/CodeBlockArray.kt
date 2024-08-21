package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.joinToCode
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_KCLASS
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_LITERAL
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_MEMBER
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_NAME
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_TYPE
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asMemberName
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
data class CodeBlockArray<T>(val format: String, val items: Collection<T> = emptyList()) : Builder<CodeBlock> {
  companion object {

    fun stringArray(vararg items: String) = CodeBlockArray(FORMAT_STRING, items.toList())

    fun enumArray(vararg items: Enum<*>): CodeBlockArray<MemberName> = CodeBlockArray(FORMAT_MEMBER, items.map { it.asMemberName() }.toList())

    fun kclassArray(vararg  items: KClass<*>) : CodeBlockArray<KClass<*>> = CodeBlockArray(FORMAT_KCLASS, items.toList())

    fun numberArray(vararg items: Number) : CodeBlockArray<Number> = CodeBlockArray(FORMAT_LITERAL, items.toList())
  }

  constructor(format: String, item: T) : this(format, listOf(item))

  operator fun plus(item: T): CodeBlockArray<T> = copy(items = items + item)

  override fun build(): CodeBlock = items.map { buildCodeBlock(format, it) }.joinToCode(prefix = "[", suffix = "]")
}
