package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.PropertySpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.PropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec

class KotlinPropertySpecBuilder internal constructor(
  private val delegate: PropertySpecBuilder
) : BuilderSupplier<KotlinPropertySpec, PropertySpec>,
  PropertySpecSupplier,
  DelegatingBuilder<KotlinPropertySpecBuilder, PropertySpecBuilderReceiver> {


  override fun builder(block: PropertySpecBuilderReceiver) = apply {
    delegate { block() }
  }

  override fun build(): KotlinPropertySpec {
    val spec = delegate.build()
    return KotlinPropertySpec(spec = spec)
  }

  override fun get(): PropertySpec = build().get()
}
