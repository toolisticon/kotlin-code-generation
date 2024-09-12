package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import kotlin.reflect.KClass

/**
 * Represents an annotation class.
 */
@ExperimentalKotlinPoetApi
data class KotlinAnnotationClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinAnnotationClassSpec>, KotlinAnnotationClassSpecSupplier, KotlinDocumentableSpec {

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override fun spec(): KotlinAnnotationClassSpec = this
  override fun get(): TypeSpec = spec
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinAnnotationClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnnotationClassSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
