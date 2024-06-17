package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpec


class KotlinAnonymousClassSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinAnonymousClassSpec, TypeSpec>, TypeSpecSupplier, DelegatingBuilder<KotlinAnonymousClassSpecBuilder, TypeSpecBuilderReceiver> {

  override fun build(): KotlinAnonymousClassSpec = KotlinAnonymousClassSpec(delegate.build())

  override fun get(): TypeSpec = build().get()

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }
}
