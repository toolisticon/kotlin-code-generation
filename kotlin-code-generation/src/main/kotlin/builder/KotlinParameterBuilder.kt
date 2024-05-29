package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpec
import io.toolisticon.kotlin.generation.spec.ParameterSpecSupplier
import kotlin.reflect.KClass

class KotlinParameterBuilder internal constructor(delegate: ParameterSpec.Builder) : KotlinPoetSpecBuilder<KotlinParameterBuilder, KotlinParameterSpec, ParameterSpec, ParameterSpec.Builder>(
  delegate = delegate
), ParameterSpecSupplier, KotlinAnnotatableBuilder<KotlinParameterBuilder> {

  @Suppress("ClassName")
  object builder : ToKotlinPoetSpecBuilder<KotlinParameterSpec,KotlinParameterBuilder> {
    operator fun invoke(name: String, type: KClass<*>) = invoke(name, type.asTypeName())
    operator fun invoke(name: String, type: TypeName) = KotlinParameterBuilder(
      delegate = ParameterSpec.builder(name, type)
    )

    override fun invoke(spec: KotlinParameterSpec): KotlinParameterBuilder = KotlinParameterBuilder(delegate = spec.get().toBuilder())
  }

  override fun build(): KotlinParameterSpec = KotlinParameterSpec(spec = delegate.build())

  override fun get(): ParameterSpec = build().get()

  override fun addAnnotation(annotationSpec: KotlinAnnotationSpec): KotlinParameterBuilder = apply {
    delegate.addAnnotation(annotationSpec.get())
  }

  override fun addAnnotation(annotation: ClassName): KotlinParameterBuilder = apply {
    delegate.addAnnotation(annotation)
  }

  override fun addAnnotation(annotation: KClass<*>): KotlinParameterBuilder = apply {
    delegate.addAnnotation(annotation)
  }

  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>): KotlinParameterBuilder = apply {
    TODO("Not yet implemented")
  }

  override fun removeAnnotation(annotation: KClass<*>): KotlinParameterBuilder = apply {
    TODO("Not yet implemented")
  }
}
