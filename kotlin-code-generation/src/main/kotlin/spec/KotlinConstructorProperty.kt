package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.ConstructorPropertySupplier

data class KotlinConstructorProperty(
  val property: KotlinPropertySpec,
  val parameter: KotlinParameterSpec,
) : ConstructorPropertySupplier {

  val name: String = property.name
  val type: TypeName = property.type

  override fun get(): KotlinConstructorProperty = this
}
