package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*

sealed interface PoetSpecBuilder<SELF, BUILDER, SPEC> {
  val builder: BUILDER

  operator fun invoke(block: BUILDER.() -> Unit): SELF

  fun build(): SPEC
}

class AnnotationSpecBuilder(override val builder: AnnotationSpec.Builder) : PoetSpecBuilder<AnnotationSpecBuilder, AnnotationSpec.Builder, AnnotationSpec> {
  override fun invoke(block: AnnotationSpec.Builder.() -> Unit): AnnotationSpecBuilder = apply { builder.block() }
  override fun build(): AnnotationSpec = builder.build()
}

class FileSpecBuilder(override val builder: FileSpec.Builder) : PoetSpecBuilder<FileSpecBuilder, FileSpec.Builder, FileSpec> {
  override fun invoke(block: FileSpec.Builder.() -> Unit): FileSpecBuilder = apply { builder.block() }
  override fun build(): FileSpec = builder.build()
}

class FunSpecBuilder(override val builder: FunSpec.Builder) : PoetSpecBuilder<FunSpecBuilder, FunSpec.Builder, FunSpec> {
  override fun invoke(block: FunSpec.Builder.() -> Unit): FunSpecBuilder = apply { builder.block() }
  override fun build(): FunSpec = builder.build()
}

class ParameterSpecBuilder(override val builder: ParameterSpec.Builder) : PoetSpecBuilder<ParameterSpecBuilder, ParameterSpec.Builder, ParameterSpec> {
  override fun invoke(block: ParameterSpec.Builder.() -> Unit): ParameterSpecBuilder = apply { builder.block() }
  override fun build(): ParameterSpec = builder.build()
}

class PropertySpecBuilder(override val builder: PropertySpec.Builder) : PoetSpecBuilder<PropertySpecBuilder, PropertySpec.Builder, PropertySpec> {
  override fun invoke(block: PropertySpec.Builder.() -> Unit): PropertySpecBuilder = apply { builder.block() }
  override fun build(): PropertySpec = builder.build()
}

class TypeAliasSpecBuilder(override val builder: TypeAliasSpec.Builder) : PoetSpecBuilder<TypeAliasSpecBuilder, TypeAliasSpec.Builder, TypeAliasSpec> {
  override fun invoke(block: TypeAliasSpec.Builder.() -> Unit): TypeAliasSpecBuilder = apply { builder.block() }
  override fun build(): TypeAliasSpec = builder.build()
}

class TypeSpecBuilder(override val builder: TypeSpec.Builder) : PoetSpecBuilder<TypeSpecBuilder, TypeSpec.Builder, TypeSpec> {
  override fun invoke(block: TypeSpec.Builder.() -> Unit): TypeSpecBuilder = apply { builder.block() }
  override fun build(): TypeSpec = builder.build()
}

typealias AnnotationSpecBuilderReceiver = AnnotationSpec.Builder.() -> Unit
typealias FileSpecBuilderReceiver = FileSpec.Builder.() -> Unit
typealias FunSpecBuilderReceiver = FunSpec.Builder.() -> Unit
typealias ParameterSpecBuilderReceiver = ParameterSpec.Builder.() -> Unit
typealias PropertySpecBuilderReceiver = PropertySpec.Builder.() -> Unit
typealias TypeSpecBuilderReceiver = TypeSpec.Builder.() -> Unit
typealias TypeAliasSpecBuilderReceiver = TypeAliasSpec.Builder.() -> Unit

