package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Wraps [ParameterSpec.Builder] for typesafe access.
 */
class ParameterSpecBuilder(
  override val builder: ParameterSpec.Builder
) : PoetSpecBuilder<ParameterSpecBuilder, ParameterSpec.Builder, ParameterSpec, ParameterSpecSupplier>,
  PoetAnnotatableBuilder<ParameterSpecBuilder>,
  PoetTaggableBuilder<ParameterSpecBuilder>,
  PoetDocumentableBuilder<ParameterSpecBuilder> {
  companion object {
    fun ParameterSpec.Builder.wrap() = ParameterSpecBuilder(this)

    fun builder(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpecBuilder = ParameterSpec.builder(name, type, *modifiers).wrap()

    fun builder(name: String, type: Type, vararg modifiers: KModifier): ParameterSpecBuilder = builder(name, type.asTypeName(), *modifiers)

    fun builder(name: String, type: KClass<*>, vararg modifiers: KModifier): ParameterSpecBuilder = ParameterSpec.builder(name, type.asTypeName(), *modifiers).wrap()

    fun builder(name: String, type: TypeName, modifiers: Iterable<KModifier>): ParameterSpecBuilder = ParameterSpec.builder(name, type, modifiers).wrap()

    fun builder(name: String, type: Type, modifiers: Iterable<KModifier>): ParameterSpecBuilder = ParameterSpec.builder(name, type.asTypeName(), modifiers).wrap()

    fun builder(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): ParameterSpecBuilder = ParameterSpec.builder(name, type.asTypeName(), modifiers).wrap()

    fun unnamed(type: KClass<*>): ParameterSpec = ParameterSpec.unnamed(type.asTypeName())

    fun unnamed(type: Type): ParameterSpec = ParameterSpec.unnamed(type.asTypeName())

    fun unnamed(type: TypeName): ParameterSpec = ParameterSpec.unnamed(type)
  }


  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = apply { builder.addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = apply { builder.addAnnotations(annotationSpecs) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = apply { builder.addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = apply { builder.addKdoc(block) }

  fun addModifiers(vararg modifiers: KModifier): ParameterSpecBuilder = apply { builder.addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): ParameterSpecBuilder = apply { builder.addModifiers(modifiers) }
  fun defaultValue(format: String, vararg args: Any?): ParameterSpecBuilder = apply { builder.defaultValue(format, *args) }
  fun defaultValue(codeBlock: CodeBlock?): ParameterSpecBuilder = apply { builder.defaultValue(codeBlock) }

  override fun tag(type: KClass<*>, tag: Any?): ParameterSpecBuilder = apply { builder.tag(type, tag) }

  override fun build(): ParameterSpec = builder.build()
}

interface ParameterSpecSupplier : PoetSpecSupplier<ParameterSpec>
typealias ParameterSpecBuilderReceiver = ParameterSpec.Builder.() -> Unit

