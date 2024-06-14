package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.*
import java.util.function.Supplier

sealed interface KotlinPoetSpec<T : Any> : SpecSupplier<T> {
  val code: String get() = get().toString()
}

sealed interface KotlinPoetTypeSpec : KotlinPoetSpec<TypeSpec>, TypeSpecSupplier, WithAnnotationSpecs, Documentable {
  override fun get(): TypeSpec

  override val annotations: List<KotlinAnnotationSpec> get() = KotlinAnnotationSpec.of(get().annotations)
}

sealed interface KotlinPoetNamedTypeSpec : KotlinPoetTypeSpec, WithClassName

interface SpecSupplier<T> : Supplier<T>
interface AnnotationSpecSupplier : SpecSupplier<AnnotationSpec>
interface FileSpecSupplier : SpecSupplier<FileSpec>
interface FunSpecSupplier : SpecSupplier<FunSpec>
interface ParameterSpecSupplier : SpecSupplier<ParameterSpec>
interface PropertySpecSupplier : SpecSupplier<PropertySpec>
interface TypeAliasSpecSupplier : SpecSupplier<TypeAliasSpec>
interface TypeSpecSupplier : SpecSupplier<TypeSpec>
interface DataClassSpecSupplier : SpecSupplier<TypeSpec>, TypeSpecSupplier
interface ConstructorPropertySupplier : SpecSupplier<KotlinConstructorProperty>, WithName


interface KotlinPoetSpecToBuilder<BUILDER> {
  fun toBuilder(): BUILDER
}

