package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.joinToCode
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildCodeBlock

@ExperimentalKotlinPoetApi
data class CodeBlockArray<T>(val format: String, val items: Collection<T> = emptyList()) : Builder<CodeBlock> {

  constructor(format: String, item: T) : this(format, listOf(item))

  operator fun plus(item: T): CodeBlockArray<T> = copy(items = items + item)

  override fun build(): CodeBlock = items.map { buildCodeBlock(format, it) }.joinToCode(prefix = "[", suffix = "]")
}
