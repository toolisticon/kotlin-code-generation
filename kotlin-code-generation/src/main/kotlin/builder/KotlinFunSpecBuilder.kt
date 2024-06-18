package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder
import io.toolisticon.kotlin.generation.poet.FunSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinFunSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier

class KotlinFunSpecBuilder internal constructor(
  private val delegate: FunSpecBuilder
) : BuilderSupplier<KotlinFunSpec, FunSpec>,
  KotlinFunSpecSupplier,
  DelegatingBuilder<KotlinFunSpecBuilder, FunSpecBuilderReceiver> {

//companion object {
//    fun builder(name: String) = KotlinFunSpecBuilder(FunSpecBuilder.builder(name))
//  }

  override fun builder(block: FunSpecBuilderReceiver): KotlinFunSpecBuilder = apply {
    delegate { block() }
  }

  override fun build(): KotlinFunSpec = KotlinFunSpec(spec = delegate.build())
  override fun spec(): KotlinFunSpec = build()
  override fun get(): FunSpec = build().get()
}
