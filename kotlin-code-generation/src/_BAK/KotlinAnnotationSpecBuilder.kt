package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterizedTypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.AnnotationSpecBuilder
import io.toolisticon.kotlin.generation._BAK.poet.AnnotationSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation._BAK.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation._BAK.KotlinAnnotationSpec
import kotlin.reflect.KClass

class KotlinAnnotationSpecBuilder internal constructor(
  private val delegate: AnnotationSpecBuilder
) : Builder<KotlinAnnotationSpec>, AnnotationSpecSupplier {
  companion object {
    fun builder(type: ClassName): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(AnnotationSpecBuilder.builder(type))
    fun builder(type: ParameterizedTypeName): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(AnnotationSpecBuilder.Companion.builder(type))
    fun builder(type: KClass<out Annotation>): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(AnnotationSpecBuilder.builder(type))

    fun from(spec: KotlinAnnotationSpec) = KotlinAnnotationSpecBuilder(delegate = spec.get().toBuilder().wrap())
  }

  operator fun invoke(block: AnnotationSpecBuilder.() -> Unit): KotlinAnnotationSpecBuilder = apply {
    delegate.block()
  }

  fun addMember(format: String, vararg args: Any): KotlinAnnotationSpecBuilder = apply {
    delegate.addMember(format, *args)
  }

  fun addMember(codeBlock: CodeBlock): KotlinAnnotationSpecBuilder = apply {
    delegate.addMember(codeBlock)
  }

  fun addKClassMember(name: String, klass: KClass<*>) = addMember("$name = %T::class", klass)

  fun addStringMember(name: String, value: String) = addMember("$name = %S", value)


  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(spec = delegate.build())
  override fun get(): AnnotationSpec = build().get()
}
