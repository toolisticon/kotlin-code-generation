package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinInterfaceSpec
import io.toolisticon.kotlin.generation.spec.KotlinInterfaceSpecSupplier

class KotlinInterfaceSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinInterfaceSpec, TypeSpec>, KotlinInterfaceSpecSupplier, DelegatingBuilder<KotlinInterfaceSpecBuilder, TypeSpecBuilderReceiver> {
//  companion object {
//    fun builder(className: ClassName) = KotlinInterfaceSpecBuilder(
//      delegate = TypeSpecBuilder.Companion.interfaceBuilder(className)
//    )
//  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinInterfaceSpec = KotlinInterfaceSpec(spec = delegate.build())
  override fun spec(): KotlinInterfaceSpec = build()
  override fun get(): TypeSpec = build().get()
}
