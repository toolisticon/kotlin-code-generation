package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.CodeBlock.Builder

@JvmInline
value class CodeBlockBuilder(private val builder: CodeBlock.Builder) : KotlinPoetBuilderSupplier<CodeBlock, CodeBlock.Builder> {

  fun isEmpty(): Boolean = builder.isEmpty()

  fun isNotEmpty(): Boolean = builder.isNotEmpty()

  fun addNamed(format: String, arguments: Map<String, *>): CodeBlockBuilder = apply {
    builder.addNamed(format, arguments)
  }

  fun add(format: String, vararg args: Any?): CodeBlockBuilder = apply {
    builder.add(format, *args)
  }

  fun beginControlFlow(controlFlow: String, vararg args: Any?): CodeBlockBuilder = apply {
    builder.beginControlFlow(controlFlow, *args)
  }

  fun nextControlFlow(controlFlow: String, vararg args: Any?): CodeBlockBuilder = apply {
    builder.nextControlFlow(controlFlow, *args)
  }

  fun endControlFlow(): CodeBlockBuilder = apply {
    builder.endControlFlow()
  }

  fun addStatement(format: String, vararg args: Any?): CodeBlockBuilder = apply {
    builder.addStatement(format, *args)
  }

  fun add(codeBlock: CodeBlock): CodeBlockBuilder = apply {
    builder.add(codeBlock)
  }

  fun add(builder: CodeBlockBuilder): CodeBlockBuilder = add(builder.build())

  fun indent(): CodeBlockBuilder = apply {
    builder.indent()
  }

  fun unindent(): CodeBlockBuilder = apply {
    builder.unindent()
  }

  fun clear(): CodeBlockBuilder = apply {
    builder.clear()
  }

  override fun build(): CodeBlock = get().build()

  override fun get(): Builder = builder
}
