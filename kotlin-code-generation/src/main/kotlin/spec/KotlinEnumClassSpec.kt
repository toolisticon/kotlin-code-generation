package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import kotlin.reflect.KClass

/**
 * Represents an enum class.
 */
@ExperimentalKotlinPoetApi
data class KotlinEnumClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec,
) : KotlinGeneratorTypeSpec<KotlinEnumClassSpec>, KotlinEnumClassSpecSupplier, KotlinDocumentableSpec {

  init {
    require(spec.isEnum) { "Not an enum spec: $spec" }
  }

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinEnumClassSpec = this
  override fun get(): TypeSpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinEnumClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinEnumClassSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
