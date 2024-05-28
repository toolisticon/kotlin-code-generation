package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinAnnotationClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinAnnotationClassSpec>(
  delegate = delegate
), TypeSpecSupplier {
  override fun build(): KotlinAnnotationClassSpec {
    TODO("Not yet implemented")
  }
}
