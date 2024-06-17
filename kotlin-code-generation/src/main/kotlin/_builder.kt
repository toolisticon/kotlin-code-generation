package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation._BAK.KotlinConstructorProperty
import java.beans.ConstructorProperties
import java.util.function.Supplier

fun interface Builder<P : Any> {
  fun build(): P
}

interface BuilderSupplier<P : Any, B : Any> : Builder<P>, Supplier<B>

interface SpecSupplier<T> : Supplier<T>
interface AnnotationSpecSupplier : SpecSupplier<AnnotationSpec>
interface ConstructorPropertySupplier : SpecSupplier<KotlinConstructorProperty>
interface FileSpecSupplier : SpecSupplier<FileSpec>
interface FunSpecSupplier : SpecSupplier<FunSpec>
interface ParameterSpecSupplier : SpecSupplier<ParameterSpec>
interface PropertySpecSupplier : SpecSupplier<PropertySpec>
interface TypeAliasSpecSupplier : SpecSupplier<TypeAliasSpec>
interface TypeSpecSupplier : SpecSupplier<TypeSpec>
interface DataClassSpecSupplier : SpecSupplier<TypeSpec>, TypeSpecSupplier
