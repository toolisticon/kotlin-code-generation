package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.PropertySpec.Builder
import io.toolisticon.kotlin.generation.BuilderSupplier
import java.lang.reflect.Type
import kotlin.reflect.KClass

typealias PropertySpecBuilderReceiver = PropertySpecBuilder.() -> Unit

@OptIn(ExperimentalKotlinPoetApi::class)
@JvmInline
value class PropertySpecBuilder(private val builder: Builder) : BuilderSupplier<PropertySpec, Builder>,
    AnnotatableBuilder<PropertySpecBuilder, PropertySpec, Builder>,
    ContextReceivableBuilder<PropertySpecBuilder, PropertySpec, Builder>,
    DocumentableBuilder<PropertySpecBuilder, PropertySpec, Builder>,
    KModifierHolderBuilder<PropertySpecBuilder, PropertySpec, Builder>,
    TaggableBuilder<PropertySpecBuilder, PropertySpec, Builder>,
    TypeVariableHolderBuilder<PropertySpecBuilder, PropertySpec, Builder>,
    OriginatingElementsHolderBuilder<PropertySpecBuilder, PropertySpec, Builder> {
  companion object {
    private fun Builder.wrap() = PropertySpecBuilder(this)
    fun builder(name: String, type: TypeName, vararg modifiers: KModifier): PropertySpecBuilder = PropertySpec.builder(name, type, *modifiers).wrap()
    fun builder(name: String, type: Type, vararg modifiers: KModifier): PropertySpecBuilder = builder(name, type.asTypeName(), *modifiers)
    fun builder(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpecBuilder = builder(name, type.asTypeName(), *modifiers)
    fun builder(name: String, type: TypeName, modifiers: Iterable<KModifier>): PropertySpecBuilder = PropertySpec.builder(name, type, modifiers).wrap()
    fun builder(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): PropertySpecBuilder = builder(name, type.asTypeName(), modifiers)
  }

  override val modifiers: MutableList<KModifier> get() = builder.modifiers
  override val typeVariables: MutableList<TypeVariableName> get() = builder.typeVariables

  fun delegate(codeBlock: CodeBlock): PropertySpecBuilder = apply { builder.delegate(codeBlock) }
  fun delegate(format: String, vararg args: Any?): PropertySpecBuilder = delegate(CodeBlock.of(format, *args))
  fun getter(getter: FunSpec?): PropertySpecBuilder = apply { builder.getter(getter) }
  fun initializer(format: String, vararg args: Any?): PropertySpecBuilder = initializer(CodeBlock.of(format, *args))
  fun initializer(codeBlock: CodeBlock?): PropertySpecBuilder = apply { builder.initializer(codeBlock) }
  fun mutable(mutable: Boolean = true): PropertySpecBuilder = apply { builder.mutable(mutable) }
  fun receiver(receiverType: TypeName?): PropertySpecBuilder = apply { builder.receiver(receiverType) }
  fun receiver(receiverType: KClass<*>): PropertySpecBuilder = receiver(receiverType.asTypeName())
  fun setter(setter: FunSpec?): PropertySpecBuilder = apply { builder.setter(setter) }

  override fun build(): PropertySpec = builder.build()
  override fun get(): Builder = builder
}

