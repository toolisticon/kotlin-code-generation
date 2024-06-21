package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

data class KotlinAnonymousClassSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinAnonymousClassSpec>, KotlinAnonymousClassSpecSupplier {
  override fun spec(): KotlinAnonymousClassSpec = this
  override fun get(): TypeSpec = spec
}
interface KotlinAnonymousClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnonymousClassSpec>, TypeSpecSupplier
