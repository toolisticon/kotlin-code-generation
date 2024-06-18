package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec

class TypeAliasSpecBuilder(
  override val builder: TypeAliasSpec.Builder
) : PoetSpecBuilder<TypeAliasSpecBuilder, TypeAliasSpec.Builder, TypeAliasSpec, TypeAliasSpecSupplier> {

  override fun invoke(block: TypeAliasSpecBuilderReceiver): TypeAliasSpecBuilder = apply {
    builder.block()
  }

  override fun build(): TypeAliasSpec = builder.build()
}

typealias TypeAliasSpecBuilderReceiver = TypeAliasSpec.Builder.() -> Unit
