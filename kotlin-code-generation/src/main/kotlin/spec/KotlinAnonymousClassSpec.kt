package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import kotlin.reflect.KClass

/**
 * Represents an anonymous class.
 */
@ExperimentalKotlinPoetApi
data class KotlinAnonymousClassSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinAnonymousClassSpec>, KotlinAnonymousClassSpecSupplier, KotlinDocumentableSpec {

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinAnonymousClassSpec = this
  override fun get(): TypeSpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinAnonymousClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnonymousClassSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
