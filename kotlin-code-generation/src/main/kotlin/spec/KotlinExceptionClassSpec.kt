package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc

@ExperimentalKotlinPoetApi
data class KotlinExceptionClassSpec(
  override val className: ClassName,
  val extends: TypeName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinExceptionClassSpec>, KotlinExceptionClassSpecSupplier, KotlinDocumentableSpec {

  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinExceptionClassSpec = this
  override fun get(): TypeSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinExceptionClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinExceptionClassSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
