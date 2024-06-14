package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.TypeName

data class KotlinConstructorProperty(
    val property: KotlinPropertySpec,
    val parameter: KotlinParameterSpec,
) : ConstructorPropertySupplier, WithName, WithType {

  override val name: String = property.name
  override val type: TypeName = property.type

  override fun get(): KotlinConstructorProperty = this
}
