package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec
import io.toolisticon.kotlin.generation.spec.TypeAliasSpecSupplier

class KotlinTypeAliasSpecBuilder internal constructor(
  private val delegate: TypeAliasSpecBuilder
) : Builder<KotlinTypeAliasSpec>, TypeAliasSpecSupplier {

  companion object {
    fun builder(name: String, type: TypeName) = KotlinTypeAliasSpecBuilder(
      delegate = TypeAliasSpecBuilder.builder(name,type)
    )
  }

  operator fun invoke(block: TypeAliasSpecBuilder.() -> Unit): KotlinTypeAliasSpecBuilder = apply {
    delegate.block()
  }

  override fun build() = KotlinTypeAliasSpec(spec = delegate.build())
  override fun get(): TypeAliasSpec = build().get()
}
