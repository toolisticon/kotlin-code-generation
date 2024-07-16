package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeAliasSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecSupplier


@JvmInline
value class KotlinTypeAliasSpec(private val spec: TypeAliasSpec) : KotlinGeneratorSpec<KotlinTypeAliasSpec,
  TypeAliasSpec,
  TypeAliasSpecSupplier>,
  KotlinTypeAliasSpecSupplier,
  KotlinDocumentableSpec {

  override val kdoc: KDoc get() = KDoc(spec.kdoc)

  override fun spec(): KotlinTypeAliasSpec = this
  override fun get(): TypeAliasSpec = spec
}

interface KotlinTypeAliasSpecSupplier : KotlinGeneratorSpecSupplier<KotlinTypeAliasSpec>, TypeAliasSpecSupplier {
  override fun get(): TypeAliasSpec = spec().get()
}
