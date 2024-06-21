package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier


data class KotlinObjectSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinObjectSpec>, KotlinObjectSpecSupplier {
  override fun spec(): KotlinObjectSpec = this
  override fun get(): TypeSpec = spec
}
interface KotlinObjectSpecSupplier : KotlinGeneratorSpecSupplier<KotlinObjectSpec>, TypeSpecSupplier
