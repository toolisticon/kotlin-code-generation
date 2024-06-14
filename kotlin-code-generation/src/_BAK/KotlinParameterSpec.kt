package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName

@JvmInline
value class KotlinParameterSpec(private val spec: ParameterSpec) : KotlinPoetSpec<ParameterSpec>, ParameterSpecSupplier, WithName, Documentable by spec  {

  override val name: String get() = spec.name

  val type: TypeName get() = spec.type

  override fun get(): ParameterSpec = this.spec
}

