package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.poet.FunSpecSupplier
import io.toolisticon.kotlin.generation.poet.KDoc

@ExperimentalKotlinPoetApi
data class KotlinFunSpec(
  private val spec: FunSpec
) : KotlinGeneratorSpec<KotlinFunSpec, FunSpec, FunSpecSupplier>,
  KotlinFunSpecSupplier,
  KotlinDocumentableSpec {

  override val kdoc: KDoc get() = KDoc(spec.kdoc)

  override fun spec(): KotlinFunSpec = this
  override fun get(): FunSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinFunSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFunSpec>, FunSpecSupplier {
  override fun get(): FunSpec = spec().get()
}
