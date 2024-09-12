package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.ParameterSpecSupplier
import kotlin.reflect.KClass

/**
 * Represents a parameter.
 */
@ExperimentalKotlinPoetApi
data class KotlinParameterSpec(
  private val spec: ParameterSpec
) : KotlinGeneratorSpec<KotlinParameterSpec, ParameterSpec, ParameterSpecSupplier>, KotlinParameterSpecSupplier, KotlinDocumentableSpec {

  val name: String get() = spec.name
  val type: TypeName get() = spec.type

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)

  override fun spec(): KotlinParameterSpec = this
  override fun get(): ParameterSpec = this.spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
@ExperimentalKotlinPoetApi
interface KotlinParameterSpecSupplier : KotlinGeneratorSpecSupplier<KotlinParameterSpec>, ParameterSpecSupplier {
  override fun get(): ParameterSpec = spec().get()
}
