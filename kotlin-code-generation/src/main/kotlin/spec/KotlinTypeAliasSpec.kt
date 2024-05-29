package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeAliasSpec

@Deprecated("Not implemented yet!")
@JvmInline
value class KotlinTypeAliasSpec(private val spec: TypeAliasSpec) : KotlinPoetSpec<TypeAliasSpec>, TypeAliasSpecSupplier {
  override fun get(): TypeAliasSpec = spec
}
