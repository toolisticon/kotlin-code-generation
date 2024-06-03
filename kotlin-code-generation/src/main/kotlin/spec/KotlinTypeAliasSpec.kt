package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeAliasSpec

@JvmInline
value class KotlinTypeAliasSpec(private val spec: TypeAliasSpec) : KotlinPoetSpec<TypeAliasSpec>, TypeAliasSpecSupplier, Documentable by spec  {
  override fun get(): TypeAliasSpec = spec
}
