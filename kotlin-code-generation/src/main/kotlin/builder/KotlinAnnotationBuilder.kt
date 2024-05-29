package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec.Builder
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import kotlin.reflect.KClass

class KotlinAnnotationBuilder internal constructor(delegate: Builder) : KotlinPoetSpecBuilder<KotlinAnnotationBuilder, KotlinAnnotationSpec, AnnotationSpec, Builder>(
  delegate = delegate
), AnnotationSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetSpecBuilder<KotlinAnnotationSpec, KotlinAnnotationBuilder> {

    operator fun invoke(type: KClass<out Annotation>): KotlinAnnotationBuilder = KotlinAnnotationBuilder(
      delegate = AnnotationSpec.builder(type)
    )

    operator fun invoke(className: ClassName): KotlinAnnotationBuilder = KotlinAnnotationBuilder(
      delegate = AnnotationSpec.builder(className)
    )

    override fun invoke(spec: KotlinAnnotationSpec): KotlinAnnotationBuilder = KotlinAnnotationBuilder(spec.get().toBuilder())
  }

  fun addMember(format: String, vararg args: Any): KotlinAnnotationBuilder = apply {
    delegate.addMember(format, *args)
  }

  fun addMember(codeBlock: CodeBlock): KotlinAnnotationBuilder = apply {
    delegate.addMember(codeBlock)
  }

  fun addKClassMember(name: String, klass: KClass<*>) = addMember("$name = %T::class", klass)

  fun addStringMember(name: String, value: String) = addMember("$name = %S", value)

  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(
    spec = delegate.build()
  )

  override fun get(): AnnotationSpec = build().get()

}
