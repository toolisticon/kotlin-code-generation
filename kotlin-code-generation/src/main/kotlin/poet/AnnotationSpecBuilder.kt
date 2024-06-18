package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.asClassName
import kotlin.reflect.KClass

class AnnotationSpecBuilder(
  override val builder: AnnotationSpec.Builder
) : PoetSpecBuilder<AnnotationSpecBuilder, AnnotationSpec.Builder, AnnotationSpec, AnnotationSpecSupplier> {
  companion object {
    fun AnnotationSpec.Builder.wrap() = AnnotationSpecBuilder(this)

    @JvmStatic
    fun builder(type: ClassName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()

    @JvmStatic
    fun builder(type: ParameterizedTypeName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()


    @JvmStatic
    fun builder(type: KClass<out Annotation>): AnnotationSpecBuilder = builder(type.asClassName())
  }

  override fun invoke(block: AnnotationSpecBuilderReceiver): AnnotationSpecBuilder = apply {
    builder.block()
  }

  override fun build(): AnnotationSpec = builder.build()
}

typealias AnnotationSpecBuilderReceiver = AnnotationSpec.Builder.() -> Unit
