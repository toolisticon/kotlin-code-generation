package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.CodeBlock.Builder
import io.toolisticon.kotlin.generation.BuilderSupplier

typealias CodeBlockBuilderReceiver = CodeBlockBuilder.() -> Unit

@JvmInline
value class CodeBlockBuilder(private val builder: Builder) : BuilderSupplier<CodeBlock, Builder> {
  companion object {
    private fun Builder.wrap() = CodeBlockBuilder(this)
    fun of(format: String, vararg args: Any?): CodeBlock = CodeBlock.of(format, *args)
    fun builder(): CodeBlockBuilder = CodeBlock.builder().wrap()
  }

  fun add(format: String, vararg args: Any?): CodeBlockBuilder = apply { builder.add(format, *args) }
  fun add(codeBlock: CodeBlock): CodeBlockBuilder = apply { builder.add(codeBlock) }
  fun add(builder: CodeBlockBuilder): CodeBlockBuilder = add(builder.build())
  fun addNamed(format: String, arguments: Map<String, *>): CodeBlockBuilder = apply { builder.addNamed(format, arguments) }
  fun addStatement(format: String, vararg args: Any?): CodeBlockBuilder = apply { builder.addStatement(format, *args) }
  fun beginControlFlow(controlFlow: String, vararg args: Any?): CodeBlockBuilder = apply { builder.beginControlFlow(controlFlow, *args) }
  fun clear(): CodeBlockBuilder = apply { builder.clear() }
  fun endControlFlow(): CodeBlockBuilder = apply { builder.endControlFlow() }
  fun indent(): CodeBlockBuilder = apply { builder.indent() }
  fun isEmpty(): Boolean = builder.isEmpty()
  fun isNotEmpty(): Boolean = builder.isNotEmpty()
  fun nextControlFlow(controlFlow: String, vararg args: Any?): CodeBlockBuilder = apply { builder.nextControlFlow(controlFlow, *args) }
  fun unindent(): CodeBlockBuilder = apply { builder.unindent() }

  override fun build(): CodeBlock = get().build()
  override fun get(): Builder = builder
}
