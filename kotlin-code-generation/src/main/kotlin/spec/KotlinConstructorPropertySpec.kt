package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeName

data class KotlinConstructorPropertySpec(
  val property: KotlinPropertySpec,
  val parameter: KotlinParameterSpec,
) : KotlinConstructorPropertySpecSupplier {

  override val name: String = property.name
  val type: TypeName = property.type
  override fun spec(): KotlinConstructorPropertySpec = this
}

interface KotlinConstructorPropertySpecSupplier : KotlinGeneratorSpecSupplier<KotlinConstructorPropertySpec> {
  /**
   * Name of property
   */
  val name: String
}

internal fun toList(constructorProperties: Collection<KotlinConstructorPropertySpecSupplier>) : List<KotlinConstructorPropertySpec> = constructorProperties.map(KotlinConstructorPropertySpecSupplier::spec).toList()
