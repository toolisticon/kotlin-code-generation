package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinAnonymousClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinAnonymousClassSpec>(
  delegate = delegate
), TypeSpecSupplier {


  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinAnonymousClassSpec, KotlinAnonymousClassBuilder> {
    override fun invoke(spec: KotlinAnonymousClassSpec, kind: TypeSpec.Kind, name: String?): KotlinAnonymousClassBuilder = KotlinAnonymousClassBuilder(spec.get().toBuilder(kind,name))
  }

  override fun build(): KotlinAnonymousClassSpec {
    TODO("Not yet implemented")
  }
}
