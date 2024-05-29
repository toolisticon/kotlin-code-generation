package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec.Builder
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import kotlin.reflect.KClass

class KotlinAnnotationBuilder internal constructor(delegate: Builder) : KotlinPoetSpecBuilder<KotlinAnnotationBuilder, KotlinAnnotationSpec, AnnotationSpec, Builder>(
  delegate = delegate
), AnnotationSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder {

    operator fun invoke(type: KClass<out Annotation>): KotlinAnnotationBuilder = KotlinAnnotationBuilder(
      delegate = AnnotationSpec.builder(type)
    )

    operator fun invoke(className: ClassName): KotlinAnnotationBuilder = KotlinAnnotationBuilder(
      delegate = AnnotationSpec.builder(className)
    )
  }

  fun addMember(format: String, vararg args: Any): KotlinAnnotationBuilder = apply {
    delegate.addMember(format, *args)
  }

  fun addMember(codeBlock: CodeBlock): KotlinAnnotationBuilder = apply {
    delegate.addMember(codeBlock)
  }

  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(
    spec = delegate.build()
  )

  override fun get(): AnnotationSpec = build().get()
}
