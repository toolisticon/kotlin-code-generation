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





