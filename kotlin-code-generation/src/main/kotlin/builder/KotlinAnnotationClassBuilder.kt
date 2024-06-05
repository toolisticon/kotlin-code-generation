package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.bak.ToKotlinPoetTypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinAnnotationClassBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : Builder<KotlinAnnotationClassSpec>, TypeSpecSupplier {

  companion object {
    fun builder(className: ClassName) = KotlinAnnotationClassBuilder(
      className = className,
      delegate = TypeSpecBuilder.annotationBuilder(className)
    )
  }

  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinAnnotationClassBuilder = apply {
    delegate.block()
  }

  override fun build(): KotlinAnnotationClassSpec = KotlinAnnotationClassSpec(
    className = className,
    spec = delegate.build()
  )

  override fun get(): TypeSpec = build().get()
}
