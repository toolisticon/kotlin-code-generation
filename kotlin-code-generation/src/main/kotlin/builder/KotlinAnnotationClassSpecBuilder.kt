package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpecSupplier

class KotlinAnnotationClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinAnnotationClassSpec, TypeSpec>, KotlinAnnotationClassSpecSupplier, DelegatingBuilder<KotlinAnnotationClassSpecBuilder, TypeSpecBuilderReceiver> {

//  companion object {
//    fun builder(className: ClassName) = KotlinAnnotationClassBuilder(
//      className = className,
//      delegate = TypeSpecBuilder.annotationBuilder(className)
//    )
//  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply{
    delegate.builder.block()
  }

  override fun build(): KotlinAnnotationClassSpec = KotlinAnnotationClassSpec(
    className = className,
    spec = delegate.build()
  )

  override fun spec(): KotlinAnnotationClassSpec = build()

  override fun get(): TypeSpec = build().get()
}
