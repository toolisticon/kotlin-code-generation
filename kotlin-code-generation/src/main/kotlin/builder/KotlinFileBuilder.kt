package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.FileSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier


class KotlinFileBuilder internal constructor(delegate: FileSpec.Builder) : KotlinPoetSpecBuilder<KotlinFileBuilder, KotlinFileSpec, FileSpec, FileSpec.Builder>(
  delegate = delegate
), FileSpecSupplier, KotlinAnnotatableBuilder<KotlinFileBuilder> {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetSpecBuilder<KotlinFileSpec, KotlinFileBuilder> {
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
        this.addType(dataClass.get())
      }
    )

    operator fun invoke(dataClassBuilder: KotlinDataClassBuilder) = invoke(
      dataClass = dataClassBuilder.build()
    )

    override fun invoke(spec: KotlinFileSpec) = KotlinFileBuilder(delegate = spec.get().toBuilder())
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  override fun build(): KotlinFileSpec = KotlinFileSpec(delegate.build())
  override fun get(): FileSpec = build().get()

  override fun addAnnotation(annotationSpec: AnnotationSpec): KotlinFileBuilder = invoke {
    addAnnotation(annotationSpec)
  }
}
