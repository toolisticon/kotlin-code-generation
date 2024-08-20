package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.poet.KDoc

@ExperimentalKotlinPoetApi
data class KotlinConstructorPropertySpec(
  val property: KotlinPropertySpec,
  val parameter: KotlinParameterSpec,
) : KotlinConstructorPropertySpecSupplier, KotlinDocumentableSpec {

  override val name: String = property.name
  val type: TypeName = property.type
  override val kdoc: KDoc get() = KDoc(property.get().kdoc)

  override fun spec(): KotlinConstructorPropertySpec = this
}

@ExperimentalKotlinPoetApi
interface KotlinConstructorPropertySpecSupplier : KotlinGeneratorSpecSupplier<KotlinConstructorPropertySpec> {
  /**
   * Name of property
   */
  val name: String
}

@ExperimentalKotlinPoetApi
internal fun toList(constructorProperties: Collection<KotlinConstructorPropertySpecSupplier>) : List<KotlinConstructorPropertySpec> = constructorProperties.map(KotlinConstructorPropertySpecSupplier::spec).toList()
