package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation._BAK.KotlinClassSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier

class KotlinClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinClassSpec>(
  delegate = delegate
), TypeSpecSupplier {

class KotlinClassBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : Builder<KotlinClassSpec>, TypeSpecSupplier {

  companion object {
    fun builder(className: ClassName) = KotlinClassBuilder(
      delegate = TypeSpecBuilder.classBuilder(className)
    )
  }

  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinClassBuilder = apply {
    delegate.block()
  }

  override fun build(): KotlinClassSpec = KotlinClassSpec(spec = delegate.build())
  override fun get(): TypeSpec = build().get()
}
