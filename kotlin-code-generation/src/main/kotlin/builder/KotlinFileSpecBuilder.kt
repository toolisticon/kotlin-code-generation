package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.MemberName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.FileSpecBuilder
import io.toolisticon.kotlin.generation.poet.FileSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.FileSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecSupplier

class KotlinFileSpecBuilder internal constructor(
  private val delegate: FileSpecBuilder
) : BuilderSupplier<KotlinFileSpec, FileSpec>, KotlinFileSpecSupplier, DelegatingBuilder<KotlinFileSpecBuilder, FileSpecBuilderReceiver> {
  companion object {
    @JvmStatic
    fun builder(
      className: ClassName
    ): KotlinFileSpecBuilder = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.builder(className)
    )

    @JvmStatic
    fun builder(
      memberName: MemberName
    ): KotlinFileSpecBuilder = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.builder(memberName)
    )

    @JvmStatic
    fun builder(
      packageName: String,
      fileName: String
    ): KotlinFileSpecBuilder = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.builder(packageName, fileName)
    )

    @JvmStatic
    fun scriptBuilder(
      fileName: String,
      packageName: String = ""
    ): KotlinFileSpecBuilder = KotlinFileSpecBuilder(
      delegate = FileSpecBuilder.scriptBuilder(fileName, packageName)
    )

    @JvmStatic
    fun builder(spec: KotlinFileSpec) = builder(spec.get())

    @JvmStatic
    fun builder(spec: FileSpec) = KotlinFileSpecBuilder(delegate = spec.toBuilder().wrap())
  }

  override fun builder(block: FileSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun addAnnotation(annotationSpec: AnnotationSpec): KotlinFileSpecBuilder = builder {
    addAnnotation(annotationSpec)
  }

  override fun build(): KotlinFileSpec {
    val spec = delegate.build()
    return KotlinFileSpec(spec = spec)
  }

  override fun spec(): KotlinFileSpec = build()
  override fun get(): FileSpec = build().get()
}

typealias KotlinFileSpecBuilderReceiver = KotlinFileSpecBuilder.() -> Unit