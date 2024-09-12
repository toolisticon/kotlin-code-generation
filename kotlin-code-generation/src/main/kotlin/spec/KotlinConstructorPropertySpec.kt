package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.poet.KDoc
import kotlin.reflect.KClass

/**
 * Represents a constructor property, wraps parameter and property for easier
 * creation of constructors with property..
 */
@ExperimentalKotlinPoetApi
data class KotlinConstructorPropertySpec(
  val property: KotlinPropertySpec,
  val parameter: KotlinParameterSpec,
) : KotlinConstructorPropertySpecSupplier, KotlinDocumentableSpec {

  override fun <T : Any> tag(type: KClass<T>): T? = property.get().tag(type)
  override val name: String = property.name
  val type: TypeName = property.type
  override val kdoc: KDoc get() = KDoc(property.get().kdoc)

  override fun spec(): KotlinConstructorPropertySpec = this
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinConstructorPropertySpecSupplier : KotlinGeneratorSpecSupplier<KotlinConstructorPropertySpec> {
  /**
   * Name of property
   */
  val name: String
}

/**
 * Convert collection to list..
 */
@ExperimentalKotlinPoetApi
internal fun toList(constructorProperties: Collection<KotlinConstructorPropertySpecSupplier>): List<KotlinConstructorPropertySpec> = constructorProperties.map(KotlinConstructorPropertySpecSupplier::spec).toList()
