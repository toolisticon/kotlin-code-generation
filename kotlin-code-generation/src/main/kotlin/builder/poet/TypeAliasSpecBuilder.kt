package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeVariableName

@JvmInline
value class TypeAliasSpecBuilder(private val builder: TypeAliasSpec.Builder) : KotlinPoetBuilderSupplier<TypeAliasSpec, TypeAliasSpec.Builder>,
  AnnotatableBuilder<TypeAliasSpecBuilder, TypeAliasSpec, TypeAliasSpec.Builder>,
  DocumentableBuilder<TypeAliasSpecBuilder, TypeAliasSpec, TypeAliasSpec.Builder>,
  TaggableBuilder<TypeAliasSpecBuilder, TypeAliasSpec, TypeAliasSpec.Builder> {

  val modifiers: MutableSet<KModifier> get() = builder.modifiers
  val typeVariables: MutableSet<TypeVariableName> get() = builder.typeVariables

  fun addModifiers(vararg modifiers: KModifier): TypeAliasSpecBuilder = apply {
    builder.addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): TypeAliasSpecBuilder = apply {
    builder.addModifiers(modifiers)
  }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): TypeAliasSpecBuilder = apply {
    builder.addTypeVariables(typeVariables)
  }

  fun addTypeVariable(typeVariable: TypeVariableName): TypeAliasSpecBuilder = apply {
    builder.addTypeVariable(typeVariable)
  }

  override fun build(): TypeAliasSpec = builder.build()
  override fun get(): TypeAliasSpec.Builder = builder
}
