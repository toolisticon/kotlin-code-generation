package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.PropertySpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec
import io.toolisticon.kotlin.generation.spec.PropertySpecSupplier

@Deprecated("Not implemented yet!")
class KotlinPropertyBuilder internal constructor(delegate: PropertySpec.Builder) : KotlinPoetSpecBuilder<KotlinPropertyBuilder, KotlinPropertySpec, PropertySpec, PropertySpec.Builder>(
  delegate = delegate
), PropertySpecSupplier {

  override fun build(): KotlinPropertySpec {
    TODO("Not yet implemented")
  }

  override fun get(): PropertySpec = build().get()
}
