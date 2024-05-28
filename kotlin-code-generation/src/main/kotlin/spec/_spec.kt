package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.*
import java.util.function.Supplier

sealed interface KotlinPoetSpec<T : Any> {
  val spec: T

  val code: String get() = spec.toString()

}

sealed interface KotlinPoetTypeSpec : KotlinPoetSpec<TypeSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec = spec
}

interface AnnotationSpecSupplier : Supplier<AnnotationSpec>
interface FileSpecSupplier : Supplier<FileSpec>
interface FunSpecSupplier : Supplier<FunSpec>
interface ParameterSpecSupplier : Supplier<ParameterSpec>
interface PropertySpecSupplier : Supplier<PropertySpec>
interface TypeAliasSpecSupplier : Supplier<TypeAliasSpec>
interface TypeSpecSupplier : Supplier<TypeSpec>
interface DataClassSpecSupplier : Supplier<TypeSpec>, TypeSpecSupplier



