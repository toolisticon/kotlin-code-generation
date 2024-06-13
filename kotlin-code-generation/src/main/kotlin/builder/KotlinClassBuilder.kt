package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinClassSpec>(
  delegate = delegate
), TypeSpecSupplier {


  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinClassSpec, KotlinClassBuilder> {
    override fun invoke(spec: KotlinClassSpec, kind: TypeSpec.Kind, name: String?): KotlinClassBuilder = KotlinClassBuilder(spec.get().toBuilder(kind,name))
  }


  override fun build(): KotlinClassSpec = KotlinClassSpec(spec = delegate.build())
}
