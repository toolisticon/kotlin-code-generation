package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpec
import mu.KLogging

class KotlinEnumClassSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinEnumClassSpec, TypeSpec>, TypeSpecSupplier, DelegatingBuilder<KotlinEnumClassSpecBuilder, TypeSpecBuilderReceiver> {

//  companion object : KLogging() {
//    fun builder(className: ClassName) = KotlinEnumClassSpecBuilder(
//      delegate = TypeSpecBuilder.enumBuilder(className)
//    )
//  }


  fun addEnumConstant(name: String) = apply {
    delegate.addEnumConstant(name)
  }

  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(delegate.build())
  override fun get(): TypeSpec = build().get()

}
