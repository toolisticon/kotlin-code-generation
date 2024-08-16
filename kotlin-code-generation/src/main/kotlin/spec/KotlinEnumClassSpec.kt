package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

@ExperimentalKotlinPoetApi
data class KotlinEnumClassSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinEnumClassSpec>, KotlinEnumClassSpecSupplier, KotlinDocumentableSpec {

  init {
    require(spec.isEnum) { "Not an enum spec: $spec" }
  }
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinEnumClassSpec = this
  override fun get(): TypeSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinEnumClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinEnumClassSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec  = spec().get()
}
