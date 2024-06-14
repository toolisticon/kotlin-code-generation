package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.ParameterSpecBuilder
import io.toolisticon.kotlin.generation._BAK.KotlinParameterSpec
import io.toolisticon.kotlin.generation._BAK.ParameterSpecSupplier

class KotlinParameterSpecBuilder internal constructor(
  private val delegate: ParameterSpecBuilder
) : Builder<KotlinParameterSpec>, ParameterSpecSupplier {

  companion object {
    fun builder(name: String, type: TypeName) = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type)
    )
  }

  operator fun invoke(block: ParameterSpecBuilder.() -> Unit): KotlinParameterSpecBuilder = apply {
    delegate.block()
  }


  fun addAnnotation(annotationSpec: AnnotationSpec): KotlinParameterSpecBuilder = invoke {
    addAnnotation(annotationSpec)
  }

  override fun build(): KotlinParameterSpec = KotlinParameterSpec(spec = delegate.build())
  override fun get(): ParameterSpec = build().get()
}
