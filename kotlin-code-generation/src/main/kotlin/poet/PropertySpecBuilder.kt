package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import javax.lang.model.element.Element
import kotlin.reflect.KClass

class PropertySpecBuilder(
  override val builder: PropertySpec.Builder
) : PoetSpecBuilder<PropertySpecBuilder, PropertySpec.Builder, PropertySpec, PropertySpecSupplier>,
  AnnotatableBuilder<PropertySpecBuilder>,
  ContextReceivableBuilder<PropertySpecBuilder>,
  DocumentableBuilder<PropertySpecBuilder>,
  OriginatingElementsHolderBuilder<PropertySpecBuilder> {
  companion object {
    fun PropertySpec.Builder.wrap() = PropertySpecBuilder(builder = this)

    @JvmStatic
    fun builder(
      name: String,
      type: TypeName,
      vararg modifiers: KModifier,
    ): PropertySpecBuilder = PropertySpec.builder(name, type, *modifiers).wrap()

    @JvmStatic
    fun builder(name: String, type: Type, vararg modifiers: KModifier): PropertySpecBuilder = builder(
      name = name,
      type = type.asTypeName(),
      modifiers = modifiers
    )

    @JvmStatic
    fun builder(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpecBuilder = builder(name, type.asTypeName(), *modifiers)

    @JvmStatic
    fun builder(name: String, type: TypeName, modifiers: Iterable<KModifier>): PropertySpecBuilder = PropertySpec.builder(name, type, modifiers).wrap()

    @JvmStatic
    fun builder(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): PropertySpecBuilder = PropertySpec.builder(name, type.asTypeName(), modifiers).wrap()
  }


  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = invoke { addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = invoke { addAnnotations(annotationSpecs) }

  // ContextReceiverBuilder
  @ExperimentalKotlinPoetApi
  override fun contextReceivers(receiverTypes: Iterable<TypeName>) = invoke { contextReceivers(receiverTypes) }

  @ExperimentalKotlinPoetApi
  override fun contextReceivers(vararg receiverTypes: TypeName) = invoke { contextReceivers(*receiverTypes) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = invoke { addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = invoke { addKdoc(block) }

  // OriginatingElementBuilder
  override fun addOriginatingElement(originatingElement: Element) = invoke { addOriginatingElement(originatingElement) }

  fun mutable(mutable: Boolean = true): PropertySpecBuilder = invoke { mutable(mutable) }
  fun addModifiers(vararg modifiers: KModifier): PropertySpecBuilder = invoke { addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): PropertySpecBuilder = invoke { addModifiers(modifiers) }
  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): PropertySpecBuilder = invoke { addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName): PropertySpecBuilder = invoke { addTypeVariable(typeVariable) }
  fun initializer(format: String, vararg args: Any?): PropertySpecBuilder = invoke { initializer(format, *args) }
  fun initializer(codeBlock: CodeBlock?): PropertySpecBuilder = invoke { initializer(codeBlock) }
  fun delegate(format: String, vararg args: Any?): PropertySpecBuilder = invoke { delegate(format, *args) }
  fun delegate(codeBlock: CodeBlock): PropertySpecBuilder = invoke { delegate(codeBlock) }
  fun getter(getter: FunSpec?): PropertySpecBuilder = invoke { getter(getter) }
  fun setter(setter: FunSpec?): PropertySpecBuilder = invoke { setter(setter) }
  fun receiver(receiverType: TypeName?): PropertySpecBuilder = invoke { receiver(receiverType) }
  fun receiver(receiverType: KClass<*>): PropertySpecBuilder = invoke { receiver(receiverType) }

  override fun invoke(block: PropertySpecBuilderReceiver): PropertySpecBuilder = apply {
    builder.block()
  }

  override fun build(): PropertySpec = builder.build()
}

interface PropertySpecSupplier : PoetSpecSupplier<PropertySpec>
typealias PropertySpecBuilderReceiver = PropertySpec.Builder.() -> Unit
