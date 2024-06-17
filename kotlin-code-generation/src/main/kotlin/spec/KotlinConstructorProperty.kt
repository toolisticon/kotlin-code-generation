package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec

data class KotlinConstructorProperty(
  val property: KotlinPropertySpec,
  val parameter: KotlinParameterSpec,
) : ConstructorPropertySupplier {

  val name: String = property.name
  val type: TypeName = property.type

  override fun get(): KotlinConstructorProperty = this
}
