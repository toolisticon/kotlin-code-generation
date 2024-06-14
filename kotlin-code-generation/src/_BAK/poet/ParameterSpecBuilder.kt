package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterSpec.Builder
import io.toolisticon.kotlin.generation.BuilderSupplier
import java.lang.reflect.Type
import kotlin.reflect.KClass

typealias ParameterSpecBuilderReceiver = ParameterSpecBuilder.() -> Unit

@JvmInline
value class ParameterSpecBuilder(private val builder: Builder) : BuilderSupplier<ParameterSpec, Builder>,
    AnnotatableBuilder<ParameterSpecBuilder, ParameterSpec, Builder>,
    DocumentableBuilder<ParameterSpecBuilder, ParameterSpec, Builder>,
    KModifierHolderBuilder<ParameterSpecBuilder, ParameterSpec, Builder>,
    TaggableBuilder<ParameterSpecBuilder, ParameterSpec, Builder> {
  companion object {
    private fun Builder.wrap() = ParameterSpecBuilder(this)
    fun builder(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpecBuilder = ParameterSpec.builder(name, type, *modifiers).wrap()
    fun builder(name: String, type: Type, vararg modifiers: KModifier): ParameterSpecBuilder = builder(name, type.asTypeName(), *modifiers)
    fun builder(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpecBuilder = builder(name, type.asTypeName(), *modifiers)
    fun builder(name: String, type: TypeName, modifiers: Iterable<KModifier>): ParameterSpecBuilder = ParameterSpec.builder(name, type, modifiers).wrap()
    fun builder(name: String, type: Type, modifiers: Iterable<KModifier>): ParameterSpecBuilder = ParameterSpec.builder(name, type.asTypeName(), modifiers).wrap()
    fun builder(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): ParameterSpecBuilder = builder(name, type.asTypeName(), modifiers)
    fun unnamed(type: KClass<*>): ParameterSpec = unnamed(type.asTypeName())
    fun unnamed(type: Type): ParameterSpec = unnamed(type.asTypeName())
    fun unnamed(type: TypeName): ParameterSpec = ParameterSpec.unnamed(type)
  }

  override val modifiers: MutableList<KModifier> get() = builder.modifiers

  fun defaultValue(format: String, vararg args: Any?): ParameterSpecBuilder = defaultValue(CodeBlock.of(format, *args))
  fun defaultValue(codeBlock: CodeBlock?): ParameterSpecBuilder = apply { builder.defaultValue(codeBlock) }

  override fun build(): ParameterSpec = builder.build()
  override fun get(): Builder = builder
}
