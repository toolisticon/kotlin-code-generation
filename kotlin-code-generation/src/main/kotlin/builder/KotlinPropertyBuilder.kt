package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.PropertySpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec
import io.toolisticon.kotlin.generation.spec.PropertySpecSupplier

@Deprecated("Not implemented yet!")
class KotlinPropertyBuilder internal constructor(delegate: PropertySpec.Builder) : KotlinPoetSpecBuilder<KotlinPropertyBuilder, KotlinPropertySpec, PropertySpec, PropertySpec.Builder>(
  delegate = delegate
), PropertySpecSupplier {


  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetSpecBuilder<KotlinPropertySpec, KotlinPropertyBuilder> {
    override fun invoke(spec: KotlinPropertySpec): KotlinPropertyBuilder = KotlinPropertyBuilder(spec.get().toBuilder())
  }

  override fun build(): KotlinPropertySpec = KotlinPropertySpec(spec = delegate.build())

  override fun get(): PropertySpec = build().get()
}
