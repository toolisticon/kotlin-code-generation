package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.PropertySpec

@JvmInline
value class KotlinPropertySpec(override val spec: PropertySpec) : KotlinPoetSpec<PropertySpec>, PropertySpecSupplier {
  override fun get(): PropertySpec = spec
}
