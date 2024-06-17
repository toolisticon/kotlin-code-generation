package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinInterfaceSpec

class KotlinInterfaceSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinInterfaceSpec, TypeSpec>, TypeSpecSupplier, DelegatingBuilder<KotlinInterfaceSpecBuilder, TypeSpecBuilderReceiver> {
//  companion object {
//    fun builder(className: ClassName) = KotlinInterfaceSpecBuilder(
//      delegate = TypeSpecBuilder.Companion.interfaceBuilder(className)
//    )
//  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinInterfaceSpec = KotlinInterfaceSpec(spec = delegate.build())
  override fun get(): TypeSpec = build().get()
}
