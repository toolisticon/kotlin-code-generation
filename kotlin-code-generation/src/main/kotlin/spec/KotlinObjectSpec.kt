package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import kotlin.reflect.KClass

/**
 * Represents an object.
 */
@ExperimentalKotlinPoetApi
data class KotlinObjectSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinObjectSpec>, KotlinObjectSpecSupplier, KotlinDocumentableSpec {

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinObjectSpec = this
  override fun get(): TypeSpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinObjectSpecSupplier : KotlinGeneratorSpecSupplier<KotlinObjectSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
