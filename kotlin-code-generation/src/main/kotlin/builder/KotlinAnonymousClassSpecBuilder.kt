package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpecSupplier


class KotlinAnonymousClassSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinAnonymousClassSpec, TypeSpec>, KotlinAnonymousClassSpecSupplier, DelegatingBuilder<KotlinAnonymousClassSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {
    @JvmStatic
    fun anonymousClassBuilder(): KotlinAnonymousClassSpecBuilder = KotlinAnonymousClassSpecBuilder(
      delegate = TypeSpecBuilder.anonymousClassBuilder()
    )
  }

  override fun build(): KotlinAnonymousClassSpec = KotlinAnonymousClassSpec(delegate.build())
  override fun get(): TypeSpec = build().get()
  override fun spec(): KotlinAnonymousClassSpec = build()

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }
}

typealias KotlinAnonymousClassSpecBuilderReceiver = KotlinAnonymousClassSpecBuilder.() -> Unit
