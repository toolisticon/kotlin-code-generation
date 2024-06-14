package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.FunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFunSpec

class KotlinFunSpecBuilder internal constructor(
  private val delegate: FunSpecBuilder
) : BuilderSupplier<KotlinFunSpec, FunSpec>,
  FunSpecSupplier,
  DelegatingBuilder<KotlinFunSpecBuilder, FunSpecBuilderReceiver> {

//companion object {
//    fun builder(name: String) = KotlinFunSpecBuilder(FunSpecBuilder.builder(name))
//  }

  override fun builder(block: FunSpecBuilderReceiver): KotlinFunSpecBuilder = apply {
    delegate { block() }
  }

  override fun build(): KotlinFunSpec = KotlinFunSpec(spec = delegate.build())
  override fun get(): FunSpec = build().get()
}
