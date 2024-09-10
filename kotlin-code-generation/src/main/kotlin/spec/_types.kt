@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.WithClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED

interface KotlinGeneratorSpecSupplier<GENERATOR_SPEC> {
  fun spec(): GENERATOR_SPEC
}

interface KotlinGeneratorSpec<SELF : KotlinGeneratorSpec<SELF, SPEC, SUPPLIER>, SPEC : PoetSpec, SUPPLIER : PoetSpecSupplier<SPEC>> : PoetSpecSupplier<SPEC>, KotlinGeneratorSpecSupplier<SELF> {
  override fun spec(): SELF
  val code: String get() = get().toString()
}

interface KotlinGeneratorTypeSpec<SELF : KotlinGeneratorTypeSpec<SELF>> : KotlinGeneratorSpec<SELF, TypeSpec, TypeSpecSupplier>, TypeSpecSupplier {
  override fun spec(): SELF
}

@ExperimentalKotlinPoetApi
interface KotlinDocumentableSpec {
  val kdoc: KDoc
}

/**
 * Marker interface for typeSpecs that provide a className and can be easily wrapped in a fileSpec.
 */
interface ToFileTypeSpecSupplier : TypeSpecSupplier, WithClassName

/**
 * Wraps supported typeSpec into a file without the need to create an extra builder.
 */
@ExperimentalKotlinPoetApi
fun ToFileTypeSpecSupplier.toFileSpec() = KotlinCodeGeneration.buildFile(className) {
  addType(this@toFileSpec)
}

enum class ClassSpecType {
  ANNOTATION,
  ANONYMOUS,
  DATA,
  VALUE,
  MAP,
  LIST,
  REGULAR,
  EXCEPTION
}
