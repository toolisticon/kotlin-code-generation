package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.ParameterSpec

class ParameterSpecBuilder(
  override val builder: ParameterSpec.Builder
) : PoetSpecBuilder<ParameterSpecBuilder, ParameterSpec.Builder, ParameterSpec, ParameterSpecSupplier> {

  override fun invoke(block: ParameterSpecBuilderReceiver): ParameterSpecBuilder = apply {
    builder.block()
  }

  override fun build(): ParameterSpec = builder.build()
}

typealias ParameterSpecBuilderReceiver = ParameterSpec.Builder.() -> Unit

