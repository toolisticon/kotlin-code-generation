package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

@ExperimentalKotlinPoetApi
data class KotlinObjectSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinObjectSpec>, KotlinObjectSpecSupplier, KotlinDocumentableSpec {
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinObjectSpec = this
  override fun get(): TypeSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinObjectSpecSupplier : KotlinGeneratorSpecSupplier<KotlinObjectSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
