package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class PropertySpecBuilder(
  override val builder: PropertySpec.Builder
) : PoetSpecBuilder<PropertySpecBuilder, PropertySpec.Builder, PropertySpec, PropertySpecSupplier>,
  AnnotatableBuilder<PropertySpecBuilder>,
  ContextReceivableBuilder<PropertySpecBuilder>,
  DocumentableBuilder<PropertySpecBuilder>,
  OriginatingElementsHolderBuilder<PropertySpecBuilder> {
  companion object {
    fun PropertySpec.Builder.wrap() = PropertySpecBuilder(builder = this)

    fun builder(name: String, type: TypeName, vararg modifiers: KModifier, ): PropertySpecBuilder = PropertySpec.builder(name, type, *modifiers).wrap()

    fun builder(name: String, type: Type, vararg modifiers: KModifier): PropertySpecBuilder = builder(
      name = name,
      type = type.asTypeName(),
      modifiers = modifiers
    )

    fun builder(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpecBuilder = builder(name, type.asTypeName(), *modifiers)

    fun builder(name: String, type: TypeName, modifiers: Iterable<KModifier>): PropertySpecBuilder = PropertySpec.builder(name, type, modifiers).wrap()

    fun builder(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): PropertySpecBuilder = PropertySpec.builder(name, type.asTypeName(), modifiers).wrap()
  }


  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = apply { builder.addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = apply { builder.addAnnotations(annotationSpecs) }

  // ContextReceiverBuilder
  override fun contextReceivers(receiverTypes: Iterable<TypeName>) = apply { builder.contextReceivers(receiverTypes) }

  override fun contextReceivers(vararg receiverTypes: TypeName) = apply { builder.contextReceivers(*receiverTypes) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = apply { builder.addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = apply { builder.addKdoc(block) }

  // OriginatingElementBuilder
  override fun addOriginatingElement(originatingElement: Element) = apply { builder.addOriginatingElement(originatingElement) }

  fun mutable(mutable: Boolean = true): PropertySpecBuilder = apply { builder.mutable(mutable) }
  fun addModifiers(vararg modifiers: KModifier): PropertySpecBuilder = apply { builder.addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): PropertySpecBuilder = apply { builder.addModifiers(modifiers) }
  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): PropertySpecBuilder = apply { builder.addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName): PropertySpecBuilder = apply { builder.addTypeVariable(typeVariable) }
  fun initializer(format: String, vararg args: Any?): PropertySpecBuilder = apply { builder.initializer(format, *args) }
  fun initializer(codeBlock: CodeBlock?): PropertySpecBuilder = apply { builder.initializer(codeBlock) }
  fun delegate(format: String, vararg args: Any?): PropertySpecBuilder = apply { builder.delegate(format, *args) }
  fun delegate(codeBlock: CodeBlock): PropertySpecBuilder = apply { builder.delegate(codeBlock) }
  fun getter(getter: FunSpec?): PropertySpecBuilder = apply { builder.getter(getter) }
  fun setter(setter: FunSpec?): PropertySpecBuilder = apply { builder.setter(setter) }
  fun receiver(receiverType: TypeName?): PropertySpecBuilder = apply { builder.receiver(receiverType) }
  fun receiver(receiverType: KClass<*>): PropertySpecBuilder = apply { builder.receiver(receiverType) }


  override fun build(): PropertySpec = builder.build()
}

interface PropertySpecSupplier : PoetSpecSupplier<PropertySpec>
typealias PropertySpecBuilderReceiver = PropertySpec.Builder.() -> Unit
