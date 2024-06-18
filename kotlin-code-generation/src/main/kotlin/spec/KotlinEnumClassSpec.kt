package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

data class KotlinEnumClassSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinEnumClassSpec>, KotlinEnumClassSpecSupplier {

  init {
    require(spec.isEnum) { "Not an enum spec: $spec" }
  }

  override fun spec(): KotlinEnumClassSpec = this
  override fun get(): TypeSpec = spec
}
