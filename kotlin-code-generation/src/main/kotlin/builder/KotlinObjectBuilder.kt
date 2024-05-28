package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.KotlinObjectSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinObjectBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinObjectSpec>(
  delegate = delegate
), TypeSpecSupplier {

  companion object {

    fun builder(other: TypeSpec.Builder) = KotlinObjectBuilder(other)

    fun builder(className: ClassName) = builder(
      other = TypeSpec.objectBuilder(className)
    )
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun addTypes(typeSpecSupplier: Iterable<TypeSpecSupplier>) = apply {
    typeSpecSupplier.forEach(::addType)
  }

  override fun build(): KotlinObjectSpec {
    return KotlinObjectSpec(delegate.build())
  }
}
