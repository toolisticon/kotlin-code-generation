package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.spec.KotlinConstructorProperty
import java.util.function.Supplier

interface BuilderSupplier<P : Any, B : Any> : Builder<P>, Supplier<B>

interface SpecSupplier<T> : Supplier<T>
interface AnnotationSpecSupplier1 : SpecSupplier<AnnotationSpec>
interface ConstructorPropertySupplier1 : SpecSupplier<KotlinConstructorProperty>
interface FileSpecSupplier1 : SpecSupplier<FileSpec>
interface FunSpecSupplier1 : SpecSupplier<FunSpec>
interface ParameterSpecSupplier1 : SpecSupplier<ParameterSpec>
interface PropertySpecSupplier1 : SpecSupplier<PropertySpec>
interface TypeAliasSpecSupplier1 : SpecSupplier<TypeAliasSpec>
interface TypeSpecSupplier1 : SpecSupplier<TypeSpec>

