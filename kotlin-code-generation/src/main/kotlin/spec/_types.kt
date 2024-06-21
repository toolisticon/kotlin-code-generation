package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.*

interface KotlinGeneratorSpecSupplier<GENERATOR_SPEC> {
  fun spec() : GENERATOR_SPEC
}

interface KotlinGeneratorSpec<SELF : KotlinGeneratorSpec<SELF, SPEC, SUPPLIER>, SPEC : PoetSpec, SUPPLIER : PoetSpecSupplier<SPEC>> : PoetSpecSupplier<SPEC>, KotlinGeneratorSpecSupplier<SELF> {
  override fun spec(): SELF
  val code : String get() = get().toString()
}

interface KotlinGeneratorTypeSpec<SELF : KotlinGeneratorTypeSpec<SELF>> : KotlinGeneratorSpec<SELF, TypeSpec, TypeSpecSupplier> {
  override fun spec(): SELF
}

interface KotlinAnnotationSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnnotationSpec>, AnnotationSpecSupplier
interface KotlinFileSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFileSpec>, FileSpecSupplier
interface KotlinFunSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFunSpec>, FunSpecSupplier
interface KotlinParameterSpecSupplier : KotlinGeneratorSpecSupplier<KotlinParameterSpec>, ParameterSpecSupplier
interface KotlinPropertySpecSupplier : KotlinGeneratorSpecSupplier<KotlinPropertySpec>, PropertySpecSupplier
interface KotlinTypeAliasSpecSupplier: KotlinGeneratorSpecSupplier<KotlinTypeAliasSpec>, TypeAliasSpecSupplier

interface KotlinAnnotationClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnnotationClassSpec>, TypeSpecSupplier
interface KotlinAnonymousClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnonymousClassSpec>, TypeSpecSupplier
interface KotlinClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinClassSpec>, TypeSpecSupplier
interface KotlinCompanionObjectSpecSupplier : KotlinGeneratorSpecSupplier<KotlinCompanionObjectSpec>, TypeSpecSupplier
interface KotlinDataClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinDataClassSpec>, TypeSpecSupplier
interface KotlinEnumClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinEnumClassSpec>, TypeSpecSupplier
interface KotlinInterfaceSpecSupplier : KotlinGeneratorSpecSupplier<KotlinInterfaceSpec>, TypeSpecSupplier
interface KotlinObjectSpecSupplier : KotlinGeneratorSpecSupplier<KotlinObjectSpec>, TypeSpecSupplier
interface KotlinValueClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinValueClassSpec>, TypeSpecSupplier

interface KotlinConstructorPropertySpecSupplier : KotlinGeneratorSpecSupplier<KotlinConstructorPropertySpec>
