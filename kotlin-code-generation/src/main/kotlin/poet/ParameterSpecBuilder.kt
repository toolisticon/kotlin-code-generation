package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

class ParameterSpecBuilder(
  override val builder: ParameterSpec.Builder
) : PoetSpecBuilder<ParameterSpecBuilder, ParameterSpec.Builder, ParameterSpec, ParameterSpecSupplier> {
  companion object {
    fun ParameterSpec.Builder.wrap() = ParameterSpecBuilder(this)


    @JvmStatic
    fun builder(name: String, type: TypeName, vararg modifiers: KModifier): ParameterSpecBuilder = ParameterSpec.builder(name, type, *modifiers).wrap()

    @JvmStatic
    fun builder(name: String, type: Type, vararg modifiers: KModifier): ParameterSpecBuilder =
      builder(name, type.asTypeName(), *modifiers)

    @JvmStatic
    fun builder(
      name: String,
      type: KClass<*>,
      vararg modifiers: KModifier,
    ): ParameterSpecBuilder = ParameterSpec.builder(name, type.asTypeName(), *modifiers).wrap()

    @JvmStatic
    fun builder(
      name: String,
      type: TypeName,
      modifiers: Iterable<KModifier>,
    ): ParameterSpecBuilder = ParameterSpec.builder(name, type, modifiers).wrap()

    @JvmStatic
    fun builder(
      name: String,
      type: Type,
      modifiers: Iterable<KModifier>,
    ): ParameterSpecBuilder = ParameterSpec.builder(name, type.asTypeName(), modifiers).wrap()

    @JvmStatic
    fun builder(
      name: String,
      type: KClass<*>,
      modifiers: Iterable<KModifier>,
    ): ParameterSpecBuilder = ParameterSpec.builder(name, type.asTypeName(), modifiers).wrap()

    @JvmStatic
    fun unnamed(type: KClass<*>): ParameterSpec = ParameterSpec.unnamed(type.asTypeName())

    @JvmStatic
    fun unnamed(type: Type): ParameterSpec = ParameterSpec.unnamed(type.asTypeName())

    @JvmStatic
    fun unnamed(type: TypeName): ParameterSpec = ParameterSpec.unnamed(type)
  }


  override fun invoke(block: ParameterSpecBuilderReceiver): ParameterSpecBuilder = apply {
    builder.block()
  }

  override fun build(): ParameterSpec = builder.build()
}

typealias ParameterSpecBuilderReceiver = ParameterSpec.Builder.() -> Unit

