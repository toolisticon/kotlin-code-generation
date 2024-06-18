package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

class PropertySpecBuilder(
  override val builder: PropertySpec.Builder
) : PoetSpecBuilder<PropertySpecBuilder, PropertySpec.Builder, PropertySpec, PropertySpecSupplier> {
  companion object {
    fun PropertySpec.Builder.wrap() = PropertySpecBuilder(builder = this)

    @JvmStatic
    fun builder(
      name: String,
      type: TypeName,
      vararg modifiers: KModifier,
    ): PropertySpecBuilder = PropertySpec.builder(name, type, *modifiers).wrap()

    @JvmStatic
    fun builder(name: String, type: Type, vararg modifiers: KModifier): PropertySpecBuilder = builder(
      name = name,
      type = type.asTypeName(),
      modifiers = modifiers
    )

    @JvmStatic
    fun builder(name: String, type: KClass<*>, vararg modifiers: KModifier): PropertySpecBuilder = builder(name, type.asTypeName(), *modifiers)

    @JvmStatic
    fun builder(name: String, type: TypeName, modifiers: Iterable<KModifier>): PropertySpecBuilder = PropertySpec.builder(name, type, modifiers).wrap()

    @JvmStatic
    fun builder(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): PropertySpecBuilder = PropertySpec.builder(name, type.asTypeName(), modifiers).wrap()
  }

  override fun invoke(block: PropertySpecBuilderReceiver): PropertySpecBuilder = apply {
    builder.block()
  }

  override fun build(): PropertySpec = builder.build()
}

typealias PropertySpecBuilderReceiver = PropertySpec.Builder.() -> Unit
