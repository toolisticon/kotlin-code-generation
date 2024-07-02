package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

data class KotlinInterfaceSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinInterfaceSpec>, KotlinInterfaceSpecSupplier {
  override fun spec(): KotlinInterfaceSpec = this
  override fun get(): TypeSpec = spec
}

interface KotlinInterfaceSpecSupplier : KotlinGeneratorSpecSupplier<KotlinInterfaceSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
