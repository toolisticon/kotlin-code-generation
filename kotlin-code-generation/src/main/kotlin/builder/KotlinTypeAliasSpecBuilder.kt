package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeAliasSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpecSupplier

class KotlinTypeAliasSpecBuilder internal constructor(
  private val delegate: TypeAliasSpecBuilder
) : BuilderSupplier<KotlinTypeAliasSpec, TypeAliasSpec>,
  KotlinTypeAliasSpecSupplier,
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
  override fun spec(): KotlinTypeAliasSpec = build()
  override fun get(): TypeAliasSpec = build().get()

}
