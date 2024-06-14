package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinClassSpec

class KotlinClassSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinClassSpec, TypeSpec>, TypeSpecSupplier, DelegatingBuilder<KotlinClassSpecBuilder, TypeSpecBuilderReceiver> {

//  companion object {
//    fun builder(className: ClassName) = KotlinClassBuilder(
//      delegate = TypeSpecBuilder.classBuilder(className)
//    )
//  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }
  
  override fun build(): KotlinClassSpec = KotlinClassSpec(spec = delegate.build())
  override fun get(): TypeSpec = build().get()
}
