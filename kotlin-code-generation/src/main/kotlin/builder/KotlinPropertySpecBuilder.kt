package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.PropertySpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilder
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier

class KotlinPropertySpecBuilder internal constructor(
  private val delegate: PropertySpecBuilder
) : BuilderSupplier<KotlinPropertySpec, PropertySpec>,
  KotlinPropertySpecSupplier,
  DelegatingBuilder<KotlinPropertySpecBuilder, PropertySpecBuilderReceiver> {


  override fun builder(block: PropertySpecBuilderReceiver) = apply {
    delegate { block() }
  }

  override fun build(): KotlinPropertySpec {
    val spec = delegate.build()
    return KotlinPropertySpec(spec = spec)
  }

  override fun spec(): KotlinPropertySpec = build()
  override fun get(): PropertySpec = build().get()
}
