package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass

class TypeAliasSpecBuilder(
  override val builder: TypeAliasSpec.Builder
) : PoetSpecBuilder<TypeAliasSpecBuilder, TypeAliasSpec.Builder, TypeAliasSpec, TypeAliasSpecSupplier> {
  companion object {
    fun TypeAliasSpec.Builder.wrap() = TypeAliasSpecBuilder(builder = this)

    @JvmStatic
    fun builder(name: String, type: TypeName): TypeAliasSpecBuilder = TypeAliasSpec.builder(name, type).wrap()

    @JvmStatic
    fun builder(name: String, type: KClass<*>): TypeAliasSpecBuilder = builder(name, type.asTypeName())
  }

  override fun invoke(block: TypeAliasSpecBuilderReceiver): TypeAliasSpecBuilder = apply {
    builder.block()
  }

  override fun build(): TypeAliasSpec = builder.build()
}

typealias TypeAliasSpecBuilderReceiver = TypeAliasSpec.Builder.() -> Unit
