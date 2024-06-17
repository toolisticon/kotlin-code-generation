package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpec

class KotlinEnumClassSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinEnumClassSpec, TypeSpec>, TypeSpecSupplier, DelegatingBuilder<KotlinEnumClassSpecBuilder, TypeSpecBuilderReceiver> {

//  companion object :KLogging() {
//    fun builder(className: ClassName) = KotlinEnumClassSpecBuilder(
//      delegate = TypeSpecBuilder.enumBuilder(className)
//    )
//  }

//  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinEnumClassSpecBuilder = apply {
//    delegate.block()
//  }

//  fun addEnumConstant(name: String) = apply {
//    delegate.addEnumConstant(name)
//  }
  
  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(delegate.build())
  override fun get(): TypeSpec = build().get()

}
