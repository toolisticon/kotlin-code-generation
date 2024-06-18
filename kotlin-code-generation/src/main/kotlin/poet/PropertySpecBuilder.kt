package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.PropertySpec

class PropertySpecBuilder(
  override val builder: PropertySpec.Builder
) : PoetSpecBuilder<PropertySpecBuilder, PropertySpec.Builder, PropertySpec, PropertySpecSupplier> {

  override fun invoke(block: PropertySpecBuilderReceiver): PropertySpecBuilder = apply {
    builder.block()
  }

  override fun build(): PropertySpec = builder.build()
}

typealias PropertySpecBuilderReceiver = PropertySpec.Builder.() -> Unit
