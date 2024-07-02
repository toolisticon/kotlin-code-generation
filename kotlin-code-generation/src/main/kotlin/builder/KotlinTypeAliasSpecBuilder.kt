package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpecSupplier
import kotlin.reflect.KClass

class KotlinTypeAliasSpecBuilder internal constructor(
  private val delegate: TypeAliasSpecBuilder
) : BuilderSupplier<KotlinTypeAliasSpec, TypeAliasSpec>,
  KotlinTypeAliasSpecSupplier,
  DelegatingBuilder<KotlinTypeAliasSpecBuilder, TypeAliasSpecBuilderReceiver> {

  companion object {
    @JvmStatic
    fun builder(
      name: String,
      type: TypeName
    ): KotlinTypeAliasSpecBuilder = KotlinTypeAliasSpecBuilder(
      delegate = TypeAliasSpecBuilder.builder(name, type)
    )

    @JvmStatic
    fun builder(
      name: String,
      type: KClass<*>
    ): KotlinTypeAliasSpecBuilder = builder(name, type.asTypeName())
  }

  override fun builder(block: TypeAliasSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build() = KotlinTypeAliasSpec(spec = delegate.build())
  override fun spec(): KotlinTypeAliasSpec = build()
  override fun get(): TypeAliasSpec = build().get()

}

typealias KotlinTypeAliasSpecBuilderReceiver = KotlinTypeAliasSpecBuilder.() -> Unit
