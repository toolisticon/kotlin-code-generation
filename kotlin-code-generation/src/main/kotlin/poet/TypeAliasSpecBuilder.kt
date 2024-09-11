package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

/**
 * Wraps [TypeAliasSpec.Builder] for typesafe access.
 */
class TypeAliasSpecBuilder(
  override val builder: TypeAliasSpec.Builder
) : PoetSpecBuilder<TypeAliasSpecBuilder, TypeAliasSpec.Builder, TypeAliasSpec, TypeAliasSpecSupplier>,
  TypeAliasSpecSupplier,
  PoetAnnotatableBuilder<TypeAliasSpecBuilder>,
  PoetTaggableBuilder<TypeAliasSpecBuilder>,
  PoetDocumentableBuilder<TypeAliasSpecBuilder> {
  companion object {
    fun TypeAliasSpec.Builder.wrap() = TypeAliasSpecBuilder(builder = this)

    fun builder(name: String, type: TypeName): TypeAliasSpecBuilder = TypeAliasSpec.builder(name, type).wrap()

    fun builder(name: String, type: KClass<*>): TypeAliasSpecBuilder = builder(name, type.asTypeName())
  }


  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = apply { builder.addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = apply { builder.addAnnotations(annotationSpecs) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = apply { builder.addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = apply { builder.addKdoc(block) }

  fun addModifiers(vararg modifiers: KModifier): TypeAliasSpecBuilder = apply { builder.addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): TypeAliasSpecBuilder = apply { builder.addModifiers(modifiers) }
  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): TypeAliasSpecBuilder = apply { builder.addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName): TypeAliasSpecBuilder = apply { builder.addTypeVariable(typeVariable) }

  override fun tag(type: KClass<*>, tag: Any?): TypeAliasSpecBuilder = apply { builder.tag(type, tag) }

  override fun build(): TypeAliasSpec = builder.build()
}

interface TypeAliasSpecSupplier : PoetSpecSupplier<TypeAliasSpec>
typealias TypeAliasSpecBuilderReceiver = TypeAliasSpec.Builder.() -> Unit
