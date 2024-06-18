package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeName

data class KotlinConstructorPropertySpec(
  val property: KotlinPropertySpec,
  val parameter: KotlinParameterSpec,
) : KotlinConstructorPropertySpecSupplier {

  val name: String = property.name
  val type: TypeName = property.type
  override fun spec(): KotlinConstructorPropertySpec = this
}
