package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.FunSpec

class FunSpecBuilder(
  override val builder: FunSpec.Builder
) : PoetSpecBuilder<FunSpecBuilder, FunSpec.Builder, FunSpec, FunSpecSupplier> {

  override fun invoke(block: FunSpecBuilderReceiver): FunSpecBuilder = apply {
    builder.block()
  }

  override fun build(): FunSpec = builder.build()
}

typealias FunSpecBuilderReceiver = FunSpec.Builder.() -> Unit
