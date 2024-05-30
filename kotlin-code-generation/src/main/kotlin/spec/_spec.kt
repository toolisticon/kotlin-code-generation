package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.*
import java.util.function.Supplier

sealed interface KotlinPoetSpec<T : Any> : Supplier<T> {
  val code: String get() = get().toString()
}

sealed interface KotlinPoetTypeSpec : KotlinPoetSpec<TypeSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec

  val annotations: List<KotlinAnnotationSpec> get() = KotlinAnnotationSpec.of(get().annotations)
}

sealed interface KotlinPoetNamedTypeSpec : KotlinPoetTypeSpec, WithClassName

interface AnnotationSpecSupplier : Supplier<AnnotationSpec>
interface FileSpecSupplier : Supplier<FileSpec>
interface FunSpecSupplier : Supplier<FunSpec>
interface ParameterSpecSupplier : Supplier<ParameterSpec>
interface PropertySpecSupplier : Supplier<PropertySpec>
interface TypeAliasSpecSupplier : Supplier<TypeAliasSpec>
interface TypeSpecSupplier : Supplier<TypeSpec>
interface DataClassSpecSupplier : Supplier<TypeSpec>, TypeSpecSupplier


interface KotlinPoetSpecToBuilder<BUILDER> {
  fun toBuilder(): BUILDER
}

