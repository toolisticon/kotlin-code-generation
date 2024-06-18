package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeName

data class KotlinConstructorProperty(
  val property: KotlinPropertySpec,
  val parameter: KotlinParameterSpec,
) { // TODO}: ConstructorPropertySupplier {

  val name: String = property.name
  val type: TypeName = property.type

  // TODO override fun get(): KotlinConstructorProperty = this
}
