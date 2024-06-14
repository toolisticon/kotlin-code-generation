package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec

class KotlinValueClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinValueClassSpec, TypeSpec>, TypeSpecSupplier, DelegatingBuilder<KotlinValueClassSpecBuilder, TypeSpecBuilderReceiver> {


  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate { block() }
  }

  override fun build(): KotlinValueClassSpec {
    val spec = delegate.build()
    return KotlinValueClassSpec(className = className, spec = spec)
  }

  override fun get(): TypeSpec = build().get()
}
