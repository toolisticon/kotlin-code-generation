package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeAliasSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecSupplier
import kotlin.reflect.KClass

/**
 * Represents a typealias.
 */
@ExperimentalKotlinPoetApi
data class KotlinTypeAliasSpec(
  private val spec: TypeAliasSpec
) : KotlinGeneratorSpec<KotlinTypeAliasSpec, TypeAliasSpec, TypeAliasSpecSupplier>, KotlinTypeAliasSpecSupplier, KotlinDocumentableSpec {

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)

  override fun spec(): KotlinTypeAliasSpec = this
  override fun get(): TypeAliasSpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinTypeAliasSpecSupplier : KotlinGeneratorSpecSupplier<KotlinTypeAliasSpec>, TypeAliasSpecSupplier {
  override fun get(): TypeAliasSpec = spec().get()
}
