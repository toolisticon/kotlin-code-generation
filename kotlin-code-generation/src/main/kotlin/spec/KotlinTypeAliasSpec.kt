package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeAliasSpec
import io.toolisticon.kotlin.generation.TypeAliasSpecSupplier

data class KotlinTypeAliasSpec(private val spec: TypeAliasSpec) : TypeAliasSpecSupplier {
  override fun get(): TypeAliasSpec = spec
}
