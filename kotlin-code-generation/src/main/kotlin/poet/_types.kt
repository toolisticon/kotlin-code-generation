package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.Builder
import java.util.function.Supplier

/**
 * Unfortunately, kotlin-poet specs do not share a common interface, this alias simulates that.
 */
typealias PoetSpec = Any

/**
 * Any class that can provide a kotlin-poet-native type like [com.squareup.kotlinpoet.TypeSpec], [com.squareup.kotlinpoet.ParameterSpec], ...
 * exposes this by implementing this interface.
 */
interface PoetSpecSupplier<SPEC : PoetSpec> : Supplier<SPEC>
interface AnnotationSpecSupplier : PoetSpecSupplier<AnnotationSpec>
interface FileSpecSupplier : PoetSpecSupplier<FileSpec>
interface FunSpecSupplier : PoetSpecSupplier<FunSpec>
interface ParameterSpecSupplier : PoetSpecSupplier<ParameterSpec>
interface PropertySpecSupplier : PoetSpecSupplier<PropertySpec>
interface TypeAliasSpecSupplier : PoetSpecSupplier<TypeAliasSpec>

/**
 * TypeSpec includes:
 *
 * * AnnotationClassSpec
 * * AnonymousClassSpec
 * * ClassSpec
 * * CompanionObjectSpec
 * * DataClassSpec
 * * EnumClassSpec
 * * InterfaceSpec
 * * ObjectSpec
 * * ValueClassSpec
 */
interface TypeSpecSupplier : PoetSpecSupplier<TypeSpec>

/**
 * Unfortunately, kotlin-poet spec-builders do not share a common interface, this interface simulates that.
 */
sealed interface PoetSpecBuilder<SELF : PoetSpecBuilder<SELF, BUILDER, SPEC, SUPPLIER>, BUILDER, SPEC : PoetSpec, SUPPLIER:PoetSpecSupplier<SPEC>> : Builder<SPEC>, PoetSpecSupplier<SPEC> {
  val builder: BUILDER

  operator fun invoke(block: BUILDER.() -> Unit): SELF

  override fun get(): SPEC = build()
}
