package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.PropertySpecSupplier
import kotlin.reflect.KClass

/**
 * Represents a property.
 */
@ExperimentalKotlinPoetApi
data class KotlinPropertySpec(
  private val spec: PropertySpec
) : KotlinGeneratorSpec<KotlinPropertySpec, PropertySpec, PropertySpecSupplier>,
  KotlinPropertySpecSupplier,
  KotlinDocumentableSpec {

  val name: String get() = spec.name

  val type: TypeName get() = spec.type

  val mutable: Boolean get() = spec.mutable

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)

  override fun spec(): KotlinPropertySpec = this
  override fun get(): PropertySpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinPropertySpecSupplier : KotlinGeneratorSpecSupplier<KotlinPropertySpec>, PropertySpecSupplier {
  override fun get(): PropertySpec = spec().get()
}
