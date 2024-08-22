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

interface KotlinGeneratorTypeSpec<SELF : KotlinGeneratorTypeSpec<SELF>> : KotlinGeneratorSpec<SELF, TypeSpec, TypeSpecSupplier> {
  override fun spec(): SELF
}

@ExperimentalKotlinPoetApi
interface KotlinDocumentableSpec {
  val kdoc: KDoc
}

interface ToFileTypeSpecSupplier : TypeSpecSupplier, WithClassName

@ExperimentalKotlinPoetApi
fun ToFileTypeSpecSupplier.toFileSpec() = KotlinCodeGeneration.buildFile(className) {
  addType(this@toFileSpec)
}
