package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.AnnotationSpec

@Deprecated("Not implemented yet!")
@JvmInline
value class KotlinAnnotationSpec(override val spec: AnnotationSpec) : KotlinPoetSpec<AnnotationSpec>, AnnotationSpecSupplier {
  override fun get(): AnnotationSpec = spec
}
