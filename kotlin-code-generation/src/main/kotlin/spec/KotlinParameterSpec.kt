package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.poet.ParameterSpecSupplier

data class KotlinParameterSpec(
  private val spec: ParameterSpec
) : KotlinGeneratorSpec<KotlinParameterSpec, ParameterSpec, ParameterSpecSupplier>, KotlinParameterSpecSupplier {

  val name: String get() = spec.name

  val type: TypeName get() = spec.type

  override fun spec(): KotlinParameterSpec = this
  override fun get(): ParameterSpec = this.spec
}

interface KotlinParameterSpecSupplier : KotlinGeneratorSpecSupplier<KotlinParameterSpec>, ParameterSpecSupplier {
  override fun get(): ParameterSpec = spec().get()
}
