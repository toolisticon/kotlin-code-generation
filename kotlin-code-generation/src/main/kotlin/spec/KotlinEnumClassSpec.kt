package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.TypeSpecSupplier

data class KotlinEnumClassSpec(
  private val spec: TypeSpec
) : TypeSpecSupplier {

  init {
    require(spec.isEnum) { "Not an enum spec: $spec" }
  }

  override fun get(): TypeSpec = spec
}
