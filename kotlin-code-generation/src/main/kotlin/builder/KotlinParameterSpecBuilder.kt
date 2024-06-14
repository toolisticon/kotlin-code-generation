package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ParameterSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.ParameterSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpec

class KotlinParameterSpecBuilder internal constructor(
  private val delegate: ParameterSpecBuilder
) : BuilderSupplier<KotlinParameterSpec, ParameterSpec>,
  ParameterSpecSupplier,
  DelegatingBuilder<KotlinParameterSpecBuilder, ParameterSpecBuilderReceiver> {

//  companion object {
//    fun builder(name: String, type: TypeName) = KotlinParameterSpecBuilder(
//      delegate = ParameterSpecBuilder.builder(name, type)
//    )
//  }


  override fun builder(block: ParameterSpecBuilderReceiver) = apply {
    delegate { block() }
  }


//  fun addAnnotation(annotationSpec: AnnotationSpec): KotlinParameterSpecBuilder = invoke {
//    addAnnotation(annotationSpec)
//  }

  override fun build(): KotlinParameterSpec = KotlinParameterSpec(spec = delegate.build())
  override fun get(): ParameterSpec = build().get()
}
