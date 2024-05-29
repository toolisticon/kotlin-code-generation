package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinAnnotationClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinAnnotationClassSpec>(
  delegate = delegate
), TypeSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinAnnotationClassSpec, KotlinAnnotationClassBuilder> {
    override fun invoke(spec: KotlinAnnotationClassSpec, kind: TypeSpec.Kind, name: String?): KotlinAnnotationClassBuilder = KotlinAnnotationClassBuilder(spec.get().toBuilder(kind, name))
  }


  override fun build(): KotlinAnnotationClassSpec {
    TODO("Not yet implemented")
  }
}
