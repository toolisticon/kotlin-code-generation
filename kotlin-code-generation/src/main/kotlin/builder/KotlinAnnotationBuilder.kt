package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import io.toolisticon.kotlin.generation.spec.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec

@Deprecated("Not implemented yet!")
class KotlinAnnotationBuilder internal constructor(delegate: AnnotationSpec.Builder) : KotlinPoetSpecBuilder<KotlinAnnotationBuilder, KotlinAnnotationSpec, AnnotationSpec, AnnotationSpec.Builder>(
  delegate = delegate
), AnnotationSpecSupplier {

  override fun build(): KotlinAnnotationSpec {
    TODO("Not yet implemented")
  }

  override fun get(): AnnotationSpec = build().get()
}
