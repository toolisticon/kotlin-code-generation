package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinInterfaceSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinInterfaceBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinInterfaceSpec>(
  delegate = delegate
), TypeSpecSupplier {


  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinInterfaceSpec, KotlinInterfaceBuilder> {
    override fun invoke(spec: KotlinInterfaceSpec, kind: TypeSpec.Kind, name: String?): KotlinInterfaceBuilder = KotlinInterfaceBuilder(spec.get().toBuilder(kind,name))
  }


  override fun build(): KotlinInterfaceSpec = KotlinInterfaceSpec(spec = delegate.build())
}
