package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.poet.FunSpecSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import kotlin.reflect.KClass

/**
 * Represents a function.
 */
@ExperimentalKotlinPoetApi
data class KotlinFunSpec(
  private val spec: FunSpec
) : KotlinGeneratorSpec<KotlinFunSpec, FunSpec, FunSpecSupplier>, KotlinFunSpecSupplier, KotlinDocumentableSpec {

  override val kdoc: KDoc get() = KDoc(spec.kdoc)

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override fun spec(): KotlinFunSpec = this
  override fun get(): FunSpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinFunSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFunSpec>, FunSpecSupplier {
  override fun get(): FunSpec = spec().get()
}
