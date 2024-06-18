package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeAliasSpec
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecSupplier

data class KotlinTypeAliasSpec(
  private val spec: TypeAliasSpec
) : KotlinGeneratorSpec<KotlinTypeAliasSpec, TypeAliasSpec, TypeAliasSpecSupplier>, KotlinTypeAliasSpecSupplier {
  override fun spec(): KotlinTypeAliasSpec = this
  override fun get(): TypeAliasSpec = spec
}
