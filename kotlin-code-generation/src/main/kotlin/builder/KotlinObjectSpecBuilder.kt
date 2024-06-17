package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinObjectSpec

class KotlinObjectSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinObjectSpec, TypeSpec>,
  TypeSpecSupplier,
  DelegatingBuilder<KotlinObjectSpecBuilder, TypeSpecBuilderReceiver> {

//  companion object {
//    fun builder(className: ClassName) = KotlinObjectBuilder(
//      delegate = TypeSpecBuilder.objectBuilder(className)
//    )
//  }


  //
//  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
//    delegate.addType(typeSpecSupplier.get())
//  }
//
//  fun addTypes(typeSpecSupplier: Iterable<TypeSpecSupplier>) = apply {
//    typeSpecSupplier.forEach(::addType)
//  }
  override fun builder(block: TypeSpecBuilderReceiver): KotlinObjectSpecBuilder = apply {
    delegate { block() }
  }

  override fun build(): KotlinObjectSpec = KotlinObjectSpec(spec = delegate.build())
  override fun get(): TypeSpec = build().get()
}
