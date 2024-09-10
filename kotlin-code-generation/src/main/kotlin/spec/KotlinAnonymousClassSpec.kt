package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

@JvmInline
@ExperimentalKotlinPoetApi
value class KotlinAnonymousClassSpec(private val spec: TypeSpec) : KotlinGeneratorTypeSpec<KotlinAnonymousClassSpec>,
  KotlinAnonymousClassSpecSupplier,
  KotlinDocumentableSpec {

  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinAnonymousClassSpec = this
  override fun get(): TypeSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinAnonymousClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnonymousClassSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
