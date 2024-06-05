package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.bak.BAK_KotlinPoetTypeSpecBuilder
import io.toolisticon.kotlin.generation.builder.bak.ToKotlinPoetTypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

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
