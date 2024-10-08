package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isDataClass
import io.toolisticon.kotlin.generation.poet.KDoc
import kotlin.reflect.KClass

/**
 * Represents a data class.
 */
@ExperimentalKotlinPoetApi
data class KotlinDataClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinDataClassSpec>, KotlinDataClassSpecSupplier, KotlinDocumentableSpec {

  init {
    require(spec.isDataClass) { "Not a dataClass spec: $spec." }
  }

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)

  override fun spec(): KotlinDataClassSpec = this
  override fun get(): TypeSpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinDataClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinDataClassSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
