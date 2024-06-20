package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

class TypeAliasSpecBuilder(
  override val builder: TypeAliasSpec.Builder
) : PoetSpecBuilder<TypeAliasSpecBuilder, TypeAliasSpec.Builder, TypeAliasSpec, TypeAliasSpecSupplier>,
  AnnotatableBuilder<TypeAliasSpecBuilder>,
  DocumentableBuilder<TypeAliasSpecBuilder> {
  companion object {
    fun TypeAliasSpec.Builder.wrap() = TypeAliasSpecBuilder(builder = this)

    @JvmStatic
    fun builder(name: String, type: TypeName): TypeAliasSpecBuilder = TypeAliasSpec.builder(name, type).wrap()

    @JvmStatic
    fun builder(name: String, type: KClass<*>): TypeAliasSpecBuilder = builder(name, type.asTypeName())
  }


  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = invoke { addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = invoke { addAnnotations(annotationSpecs) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = invoke { addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = invoke { addKdoc(block) }

  fun addModifiers(vararg modifiers: KModifier): TypeAliasSpecBuilder = invoke { addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): TypeAliasSpecBuilder = invoke { addModifiers(modifiers) }
  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): TypeAliasSpecBuilder = invoke { addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName): TypeAliasSpecBuilder = invoke { addTypeVariable(typeVariable) }

  override fun invoke(block: TypeAliasSpecBuilderReceiver): TypeAliasSpecBuilder = apply { builder.block() }
  override fun build(): TypeAliasSpec = builder.build()
}

interface TypeAliasSpecSupplier : PoetSpecSupplier<TypeAliasSpec>
typealias TypeAliasSpecBuilderReceiver = TypeAliasSpec.Builder.() -> Unit
