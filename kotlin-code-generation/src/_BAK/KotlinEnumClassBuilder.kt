package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation._BAK.KotlinEnumClassSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier
import mu.KLogging

class KotlinEnumClassBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : Builder<KotlinEnumClassSpec>, TypeSpecSupplier {

  companion object :KLogging() {
    fun builder(className: ClassName) = KotlinEnumClassBuilder(
      delegate = TypeSpecBuilder.enumBuilder(className)
    )
  }

  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinEnumClassBuilder = apply {
    delegate.block()
  }

  fun addEnumConstant(name: String) = apply {
    delegate.addEnumConstant(name)
  }

  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(delegate.build())
  override fun get(): TypeSpec = build().get()

}
