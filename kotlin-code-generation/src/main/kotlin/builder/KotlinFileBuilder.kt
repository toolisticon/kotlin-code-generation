package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.asClassName
import io.toolisticon.kotlin.generation.spec.*
import kotlin.reflect.KClass


class KotlinFileBuilder internal constructor(delegate: FileSpec.Builder) : KotlinPoetSpecBuilder<KotlinFileBuilder, KotlinFileSpec, FileSpec, FileSpec.Builder>(
  delegate = delegate
), FileSpecSupplier, KotlinAnnotatableBuilder<KotlinFileBuilder> {

  @Suppress("ClassName")
  object builder {
    operator fun invoke(className: ClassName, block: FileSpecBuilderReceiver = {}) = invoke(
      other = FileSpec.builder(className),
      block = block
    )

    operator fun invoke(packageName: String, fileName: String, block: FileSpecBuilderReceiver = {}) = invoke(
      className = ClassName(packageName, fileName),
      block = block
    )

    operator fun invoke(other: FileSpec.Builder, block: FileSpecBuilderReceiver = {}) = KotlinFileBuilder(
      delegate = other
    ).invoke(block)

    operator fun invoke(dataClass: KotlinDataClassSpec) = invoke(
      className = dataClass.className,
      block = {
        this.addType(dataClass.spec)
      }
    )

    operator fun invoke(dataClassBuilder: KotlinDataClassBuilder) = invoke(
      dataClass = dataClassBuilder.build()
    )
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  override fun build(): KotlinFileSpec = KotlinFileSpec(delegate.build())
  override fun get(): FileSpec = build().get()

  override fun addAnnotation(annotationSpec: KotlinAnnotationSpec): KotlinFileBuilder = apply {
    delegate.addAnnotation(annotationSpec.spec)
  }

  override fun addAnnotation(annotation: ClassName): KotlinFileBuilder = apply {
    delegate.addAnnotation(annotation)
  }

  override fun addAnnotation(annotation: KClass<*>): KotlinFileBuilder = apply {
    delegate.addAnnotation(annotation)
  }

  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>): KotlinFileBuilder = apply {
    annotationSpecs.forEach(::addAnnotation)
  }

  override fun removeAnnotation(annotation: KClass<*>): KotlinFileBuilder = apply {
    delegate.annotations.removeIf {
      it.typeName == annotation.asClassName()
    }
  }
}
