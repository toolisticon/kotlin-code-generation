package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation._BAK.KotlinInterfaceSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier

class KotlinInterfaceBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : Builder<KotlinInterfaceSpec>, TypeSpecSupplier {
  companion object {
    fun builder(className: ClassName) = KotlinInterfaceBuilder(
      delegate = TypeSpecBuilder.Companion.interfaceBuilder(className)
    )
  }

  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinInterfaceBuilder = apply {
    delegate.block()
  }

  override fun build(): KotlinInterfaceSpec = KotlinInterfaceSpec(spec = delegate.build())
  override fun get(): TypeSpec = build().get()
}
