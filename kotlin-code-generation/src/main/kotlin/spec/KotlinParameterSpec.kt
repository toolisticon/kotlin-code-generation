package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName

@JvmInline
value class KotlinParameterSpec(private val spec: ParameterSpec) : KotlinPoetSpec<ParameterSpec>, ParameterSpecSupplier {

  val name: String get() = spec.name
  val type: TypeName get() = spec.type

  override fun get(): ParameterSpec = this.spec
}
