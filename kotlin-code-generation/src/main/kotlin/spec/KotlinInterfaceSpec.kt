package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

@ExperimentalKotlinPoetApi
data class KotlinInterfaceSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinInterfaceSpec>, KotlinInterfaceSpecSupplier, KotlinDocumentableSpec {
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinInterfaceSpec = this
  override fun get(): TypeSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinInterfaceSpecSupplier : KotlinGeneratorSpecSupplier<KotlinInterfaceSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
