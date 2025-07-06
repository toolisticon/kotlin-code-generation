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

/**
 * A supplier for Kotlin generator specs, which can be used to provide a spec for code generation.
 * This is typically used in conjunction with Kotlin generator specifications.
 */
interface KotlinGeneratorSpecSupplier<GENERATOR_SPEC> {
  fun spec(): GENERATOR_SPEC
}

/**
 * A Kotlin generator specification that provides a code generation spec.
 * It extends the [PoetSpecSupplier] interface to provide the underlying spec.
 *
 * @param SELF The type of the generator spec itself, used for fluent API.
 * @param SPEC The type of the spec being generated.
 * @param SUPPLIER The type of the supplier for the spec.
 */
sealed interface KotlinGeneratorSpec<SELF : KotlinGeneratorSpec<SELF, SPEC, SUPPLIER>, SPEC : PoetSpec, SUPPLIER : PoetSpecSupplier<SPEC>> : PoetSpecSupplier<SPEC>, KotlinGeneratorSpecSupplier<SELF> {
  override fun spec(): SELF
  val code: String get() = get().toString()
}

/**
 * A Kotlin generator type specification that provides a type spec.
 * It extends the [KotlinGeneratorSpec] interface to provide the underlying type spec.
 *
 * @param SELF The type of the generator spec itself, used for fluent API.
 */
sealed interface KotlinGeneratorTypeSpec<SELF : KotlinGeneratorTypeSpec<SELF>> : KotlinGeneratorSpec<SELF, TypeSpec, TypeSpecSupplier>, TypeSpecSupplier {
  override fun spec(): SELF
}

/**
 * Marks a spec as documentable, meaning it can be documented with KDoc.
 */
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
