package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.WithClassName
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

@ExperimentalKotlinPoetApi
data class KotlinAnnotationClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec,
) : KotlinGeneratorTypeSpec<KotlinAnnotationClassSpec>, TypeSpecSupplier, KotlinAnnotationClassSpecSupplier,
  KotlinDocumentableSpec {

  override fun spec(): KotlinAnnotationClassSpec = this
  override fun get(): TypeSpec = spec
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
}

@ExperimentalKotlinPoetApi
interface KotlinAnnotationClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnnotationClassSpec>, TypeSpecSupplier, WithClassName {
  override fun get(): TypeSpec = spec().get()
}
