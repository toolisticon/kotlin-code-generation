package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinAnnotationClassBuilder internal constructor(className: ClassName, delegate: TypeSpec.Builder) : KotlinPoetNamedTypeSpecBuilder<KotlinAnnotationClassSpec>(
  className = className,
  delegate = delegate
), TypeSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinAnnotationClassSpec, KotlinAnnotationClassBuilder> {
    override fun invoke(spec: KotlinAnnotationClassSpec, kind: TypeSpec.Kind, name: String?): KotlinAnnotationClassBuilder = TODO() // FIXME  KotlinAnnotationClassBuilder(spec.get().toBuilder(kind,
    // name))
  }


  override fun build(): KotlinAnnotationClassSpec = KotlinAnnotationClassSpec(
    className = className,
    spec = delegate.build()
  )
}
