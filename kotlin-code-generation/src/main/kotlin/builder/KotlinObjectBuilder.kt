package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.bak.ToKotlinPoetTypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinObjectSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinObjectBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : Builder<KotlinObjectSpec>, TypeSpecSupplier {

  companion object {
    fun builder(className: ClassName) = KotlinObjectBuilder(
      delegate = TypeSpecBuilder.objectBuilder(className)
    )
  }

  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinObjectBuilder = apply {
    delegate.block()
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun addTypes(typeSpecSupplier: Iterable<TypeSpecSupplier>) = apply {
    typeSpecSupplier.forEach(::addType)
  }

  override fun build(): KotlinObjectSpec = KotlinObjectSpec(spec = delegate.build())
  override fun get(): TypeSpec = build().get()
}
