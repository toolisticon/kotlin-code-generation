package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec

@JvmInline
value class ParameterSpecBuilder(private val builder: ParameterSpec.Builder) : KotlinPoetBuilderSupplier<ParameterSpec, ParameterSpec.Builder>,
  AnnotatableBuilder<ParameterSpec, ParameterSpec.Builder>,
  DocumentableBuilder<ParameterSpec, ParameterSpec.Builder>,
  TaggableBuilder<ParameterSpec, ParameterSpec.Builder> {

  val modifiers: MutableList<KModifier> get() = get().modifiers

  fun addModifiers(vararg modifiers: KModifier): ParameterSpecBuilder = apply {
    get().addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): ParameterSpecBuilder = apply {
    get().addModifiers(modifiers)
  }


  fun defaultValue(format: String, vararg args: Any?): ParameterSpecBuilder =
    defaultValue(CodeBlock.of(format, *args))

  fun defaultValue(codeBlock: CodeBlock?): ParameterSpecBuilder = apply {
    get().defaultValue(codeBlock)
  }

  override fun build(): ParameterSpec = get().build()

  override fun get(): ParameterSpec.Builder = builder
}
