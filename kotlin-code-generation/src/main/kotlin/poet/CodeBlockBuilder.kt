package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.CodeBlock.Builder


class CodeBlockBuilder(
  override val builder: CodeBlock.Builder
) : PoetSpecBuilder<CodeBlockBuilder, CodeBlock.Builder, CodeBlock, CodeBlockSupplier> {
  companion object {
    private fun Builder.wrap() = CodeBlockBuilder(this)
    fun of(format: String, vararg args: Any?): CodeBlock = CodeBlock.of(format, *args)
    fun builder(): CodeBlockBuilder = CodeBlock.builder().wrap()
  }

  fun add(format: String, vararg args: Any?): CodeBlockBuilder = invoke { add(format, *args) }
  fun add(codeBlock: CodeBlock): CodeBlockBuilder = invoke { add(codeBlock) }
  fun add(builder: CodeBlockBuilder): CodeBlockBuilder = add(builder.build())
  fun addNamed(format: String, arguments: Map<String, *>): CodeBlockBuilder = invoke { addNamed(format, arguments) }
  fun addStatement(format: String, vararg args: Any?): CodeBlockBuilder = invoke { addStatement(format, *args) }
  fun beginControlFlow(controlFlow: String, vararg args: Any?): CodeBlockBuilder = invoke { beginControlFlow(controlFlow, *args) }
  fun clear(): CodeBlockBuilder = invoke { clear() }
  fun endControlFlow(): CodeBlockBuilder = invoke { endControlFlow() }
  fun indent(): CodeBlockBuilder = invoke { indent() }
  fun isEmpty(): Boolean = builder.isEmpty()
  fun isNotEmpty(): Boolean = builder.isNotEmpty()
  fun nextControlFlow(controlFlow: String, vararg args: Any?): CodeBlockBuilder = invoke { nextControlFlow(controlFlow, *args) }
  fun unindent(): CodeBlockBuilder = invoke { unindent() }

  override fun invoke(block: Builder.() -> Unit): CodeBlockBuilder = apply {
    builder.block()
  }

  override fun build(): CodeBlock = builder.build()
}

interface CodeBlockSupplier : PoetSpecSupplier<CodeBlock>
typealias CodeBlockBuilderReceiver = CodeBlockBuilder.() -> Unit
