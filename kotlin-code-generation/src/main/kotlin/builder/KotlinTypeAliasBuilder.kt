package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeAliasSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec
import io.toolisticon.kotlin.generation.spec.TypeAliasSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinTypeAliasBuilder internal constructor(delegate: TypeAliasSpec.Builder) : KotlinPoetSpecBuilder<KotlinTypeAliasBuilder, KotlinTypeAliasSpec, TypeAliasSpec, TypeAliasSpec.Builder>(
  delegate = delegate
), TypeAliasSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetSpecBuilder<KotlinTypeAliasSpec, KotlinTypeAliasBuilder> {
    override fun invoke(spec: KotlinTypeAliasSpec) = KotlinTypeAliasBuilder(delegate = spec.get().toBuilder())
  }

  override fun build() = KotlinTypeAliasSpec(spec = delegate.build())

  override fun get(): TypeAliasSpec = build().get()
}
