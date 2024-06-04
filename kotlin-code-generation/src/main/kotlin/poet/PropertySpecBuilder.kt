package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
@JvmInline
value class PropertySpecBuilder(private val builder: PropertySpec.Builder) : BuilderSupplier<PropertySpec, PropertySpec.Builder>,
  AnnotatableBuilder<PropertySpecBuilder, PropertySpec, PropertySpec.Builder>,
  ContextReceivableBuilder<PropertySpecBuilder, PropertySpec, PropertySpec.Builder>,
  DocumentableBuilder<PropertySpecBuilder, PropertySpec, PropertySpec.Builder>,
  TaggableBuilder<PropertySpecBuilder, PropertySpec, PropertySpec.Builder>,
  OriginatingElementsHolderBuilder<PropertySpecBuilder, PropertySpec, PropertySpec.Builder> {

  val modifiers: MutableList<KModifier> get() = builder.modifiers
  val typeVariables: MutableList<TypeVariableName> get() = builder.typeVariables

  fun mutable(mutable: Boolean = true): PropertySpecBuilder = apply {
    builder.mutable(mutable)
  }

  fun addModifiers(vararg modifiers: KModifier): PropertySpecBuilder = apply {
    builder.addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): PropertySpecBuilder = apply {
    builder.addModifiers(modifiers)
  }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): PropertySpecBuilder = apply {
    builder.addTypeVariables(typeVariables)
  }

  fun addTypeVariable(typeVariable: TypeVariableName): PropertySpecBuilder = apply {
    builder.addTypeVariable(typeVariable)
  }

  fun initializer(format: String, vararg args: Any?): PropertySpecBuilder =
    initializer(CodeBlock.of(format, *args))

  fun initializer(codeBlock: CodeBlock?): PropertySpecBuilder = apply {
    builder.initializer(codeBlock)
  }

  fun delegate(format: String, vararg args: Any?): PropertySpecBuilder =
    delegate(CodeBlock.of(format, *args))

  fun delegate(codeBlock: CodeBlock): PropertySpecBuilder = apply {
    builder.delegate(codeBlock)
  }

  fun getter(getter: FunSpec?): PropertySpecBuilder = apply {
    builder.getter(getter)
  }

  fun setter(setter: FunSpec?): PropertySpecBuilder = apply {
    builder.setter(setter)
  }

  fun receiver(receiverType: TypeName?): PropertySpecBuilder = apply {
    builder.receiver(receiverType)
  }

  fun receiver(receiverType: KClass<*>): PropertySpecBuilder = receiver(receiverType.asTypeName())

  override fun build(): PropertySpec = builder.build()

  override fun get(): PropertySpec.Builder = builder
}

