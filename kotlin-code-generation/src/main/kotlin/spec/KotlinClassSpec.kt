package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.TypeSpecSupplier

data class KotlinClassSpec(
  private val spec: TypeSpec
) : TypeSpecSupplier {
  override fun get(): TypeSpec = spec
}
