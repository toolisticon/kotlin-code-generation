package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.TypeSpec

class TypeSpecBuilder(
  override val builder: TypeSpec.Builder
) : PoetSpecBuilder<TypeSpecBuilder, TypeSpec.Builder, TypeSpec, TypeSpecSupplier> {

  override fun invoke(block: TypeSpecBuilderReceiver): TypeSpecBuilder = apply {
    builder.block()
  }

  override fun build(): TypeSpec = builder.build()
}

typealias TypeSpecBuilderReceiver = TypeSpec.Builder.() -> Unit
