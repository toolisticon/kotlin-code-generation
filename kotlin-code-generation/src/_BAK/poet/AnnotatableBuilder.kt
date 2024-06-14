package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.Annotatable
import com.squareup.kotlinpoet.AnnotationSpec
import io.toolisticon.kotlin.generation.BuilderSupplier

sealed interface AnnotatableBuilder<SELF : AnnotatableBuilder<SELF, S, B>, S : Annotatable, B : Annotatable.Builder<B>>
  : BuilderSupplier<S, B>, Annotatable.Builder<SELF> {
  override val annotations: MutableList<AnnotationSpec> get() = get().annotations

  fun addAnnotation(builder: AnnotationSpecBuilder): SELF = addAnnotation(builder.build())
}
