@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.WithClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import kotlin.reflect.KClass

interface KotlinGeneratorSpecSupplier<GENERATOR_SPEC> {
  fun spec(): GENERATOR_SPEC
}

sealed interface KotlinGeneratorSpec<SELF : KotlinGeneratorSpec<SELF, SPEC, SUPPLIER>, SPEC : PoetSpec, SUPPLIER : PoetSpecSupplier<SPEC>> : PoetSpecSupplier<SPEC>, KotlinGeneratorSpecSupplier<SELF> {
  override fun spec(): SELF
  val code: String get() = get().toString()
}

sealed interface KotlinGeneratorTypeSpec<SELF : KotlinGeneratorTypeSpec<SELF>> : KotlinGeneratorSpec<SELF, TypeSpec, TypeSpecSupplier>, TypeSpecSupplier {
  override fun spec(): SELF
}

@ExperimentalKotlinPoetApi
sealed interface KotlinDocumentableSpec : TaggableSpec {
  val kdoc: KDoc
}

/**
 * Marker interface for typeSpecs that provide a className and can be easily wrapped in a fileSpec.
 */
sealed interface ToFileTypeSpecSupplier : TypeSpecSupplier, WithClassName

/**
 * Wraps supported typeSpec into a file without the need to create an extra builder.
 */
@ExperimentalKotlinPoetApi
fun ToFileTypeSpecSupplier.toFileSpec() = KotlinCodeGeneration.buildFile(className) {
  addType(this@toFileSpec)
}

/**
 * Marks Spec as [com.squareup.kotlinpoet.Taggable].
 */
sealed interface TaggableSpec {
  /**
   * @see [com.squareup.kotlinpoet.Taggable.tag]
   */
  fun <T : Any> tag(type: KClass<T>): T?
}

/**
 * Reified access to [TaggableSpec.tag].
 */
inline fun <reified T : Any> TaggableSpec.tag(): T? = tag(T::class)

/**
 * Tags a spec with extra type.
 */
enum class ClassSpecType {
  MAP,
  LIST,
  EXCEPTION
}
