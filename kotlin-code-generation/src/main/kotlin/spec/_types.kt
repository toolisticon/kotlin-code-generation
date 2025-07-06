@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.WithClassName
import io.toolisticon.kotlin.generation.WithTags
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.PoetSpec
import io.toolisticon.kotlin.generation.poet.PoetSpecSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED

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
sealed interface KotlinDocumentableSpec : WithTags {
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
 * Tags a spec with extra type.
 */
enum class ClassSpecType {
  MAP,
  LIST,
  EXCEPTION
}
