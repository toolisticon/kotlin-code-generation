package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec

@JvmInline
value class ParameterSpecBuilder(private val builder: ParameterSpec.Builder) : KotlinPoetBuilderSupplier<ParameterSpec, ParameterSpec.Builder>,
  AnnotatableBuilder<ParameterSpecBuilder, ParameterSpec, ParameterSpec.Builder>,
  DocumentableBuilder<ParameterSpecBuilder, ParameterSpec, ParameterSpec.Builder>,
  TaggableBuilder<ParameterSpecBuilder, ParameterSpec, ParameterSpec.Builder> {

  val modifiers: MutableList<KModifier> get() = builder.modifiers

  fun addModifiers(vararg modifiers: KModifier): ParameterSpecBuilder = apply {
    builder.addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): ParameterSpecBuilder = apply {
    builder.addModifiers(modifiers)
  }


  fun defaultValue(format: String, vararg args: Any?): ParameterSpecBuilder =
    defaultValue(CodeBlock.of(format, *args))

  fun defaultValue(codeBlock: CodeBlock?): ParameterSpecBuilder = apply {
    builder.defaultValue(codeBlock)
  }

  override fun build(): ParameterSpec = builder.build()

  override fun get(): ParameterSpec.Builder = builder
}
