package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

data class KotlinAnnotationClassSpec(
  val className: ClassName,
  private val spec: TypeSpec,
) : KotlinGeneratorTypeSpec<KotlinAnnotationClassSpec>, TypeSpecSupplier, KotlinAnnotationClassSpecSupplier,
KotlinDocumentableSpec{

  override fun spec(): KotlinAnnotationClassSpec = this
  override fun get(): TypeSpec = spec
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
}
interface KotlinAnnotationClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnnotationClassSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec  = spec().get()
}
