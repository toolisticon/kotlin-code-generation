package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.AnnotationSpec

class AnnotationSpecBuilder(
  override val builder: AnnotationSpec.Builder
) : PoetSpecBuilder<AnnotationSpecBuilder, AnnotationSpec.Builder, AnnotationSpec, AnnotationSpecSupplier> {

  override fun invoke(block: AnnotationSpecBuilderReceiver): AnnotationSpecBuilder = apply {
    builder.block()
  }

  override fun build(): AnnotationSpec = builder.build()
}

typealias AnnotationSpecBuilderReceiver = AnnotationSpec.Builder.() -> Unit
