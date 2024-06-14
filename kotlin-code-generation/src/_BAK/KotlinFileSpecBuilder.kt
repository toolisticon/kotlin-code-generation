package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.FileSpecBuilder
import io.toolisticon.kotlin.generation._BAK.FileSpecSupplier
import io.toolisticon.kotlin.generation._BAK.KotlinFileSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier


class KotlinFileSpecBuilder internal constructor(
  private val delegate: FileSpecBuilder
) : Builder<KotlinFileSpec>, FileSpecSupplier {

  companion object {
    fun builder(className: ClassName) = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.builder(className)
    )
  }

  operator fun invoke(block: FileSpecBuilder.() -> Unit): KotlinFileSpecBuilder = apply {
    delegate.block()
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun addAnnotation(annotationSpec: AnnotationSpec): KotlinFileSpecBuilder = invoke {
    addAnnotation(annotationSpec)
  }

  override fun build(): KotlinFileSpec = KotlinFileSpec(delegate.build())
  override fun get(): FileSpec = build().get()
}
