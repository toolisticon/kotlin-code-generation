package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpec
import io.toolisticon.kotlin.generation.spec.ParameterSpecSupplier
import kotlin.reflect.KClass

class KotlinParameterBuilder internal constructor(delegate: ParameterSpec.Builder) : KotlinPoetSpecBuilder<KotlinParameterBuilder, KotlinParameterSpec, ParameterSpec, ParameterSpec.Builder>(
  delegate = delegate
), ParameterSpecSupplier, KotlinAnnotatableBuilder<KotlinParameterBuilder> {

  @Suppress("ClassName")
  object builder : ToKotlinPoetSpecBuilder<KotlinParameterSpec, KotlinParameterBuilder> {
    operator fun invoke(name: String, type: KClass<*>) = invoke(name, type.asTypeName())
    operator fun invoke(name: String, type: TypeName) = KotlinParameterBuilder(
      delegate = ParameterSpec.builder(name, type)
    )

    override fun invoke(spec: KotlinParameterSpec): KotlinParameterBuilder = KotlinParameterBuilder(delegate = spec.get().toBuilder())
  }

  override fun addAnnotation(annotationSpec: AnnotationSpec): KotlinParameterBuilder = invoke {
    addAnnotation(annotationSpec)
  }

  override fun build(): KotlinParameterSpec = KotlinParameterSpec(spec = delegate.build())

  override fun get(): ParameterSpec = build().get()

}
