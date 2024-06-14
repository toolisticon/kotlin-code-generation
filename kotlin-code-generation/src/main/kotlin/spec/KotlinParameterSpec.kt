package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.ParameterSpecSupplier

data class KotlinParameterSpec(private val spec: ParameterSpec) : ParameterSpecSupplier {

  val name: String get() = spec.name

  val type: TypeName get() = spec.type

  override fun get(): ParameterSpec = this.spec
}

