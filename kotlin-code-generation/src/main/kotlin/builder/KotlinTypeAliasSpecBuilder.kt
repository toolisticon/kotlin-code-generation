package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeAliasSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.TypeAliasSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec

class KotlinTypeAliasSpecBuilder internal constructor(
  private val delegate: TypeAliasSpecBuilder
) : BuilderSupplier<KotlinTypeAliasSpec, TypeAliasSpec>,
  TypeAliasSpecSupplier,
  DelegatingBuilder<KotlinTypeAliasSpecBuilder, TypeAliasSpecBuilderReceiver> {

//  companion object {
//    fun builder(name: String, type: TypeName) = KotlinTypeAliasSpecBuilder(
//      delegate = TypeAliasSpecBuilder.builder(name, type)
//    )
//  }

  override fun builder(block: TypeAliasSpecBuilderReceiver) = apply {
    delegate { block() }
  }

  override fun build() = KotlinTypeAliasSpec(spec = delegate.build())
  override fun get(): TypeAliasSpec = build().get()
}
