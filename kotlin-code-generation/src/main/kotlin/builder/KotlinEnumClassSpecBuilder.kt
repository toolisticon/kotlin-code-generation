package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpecSupplier

class KotlinEnumClassSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinEnumClassSpec, TypeSpec>, KotlinEnumClassSpecSupplier, DelegatingBuilder<KotlinEnumClassSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {
    @JvmStatic
    fun enumBuilder(name: String): KotlinEnumClassSpecBuilder = KotlinEnumClassSpecBuilder(
      delegate = TypeSpecBuilder.enumBuilder(name)
    )

    @JvmStatic
    fun enumBuilder(className: ClassName): KotlinEnumClassSpecBuilder = enumBuilder(className.simpleName)

  }

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

  fun addEnumConstant(name: String) = apply {
    delegate {
      addEnumConstant(name)
    }
  }

  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(delegate.build())
  override fun spec(): KotlinEnumClassSpec = build()
  override fun get(): TypeSpec = build().get()

}
