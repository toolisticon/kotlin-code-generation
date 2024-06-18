package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import kotlin.reflect.KClass

class KotlinAnnotationSpecBuilder internal constructor(
  private val delegate: AnnotationSpecBuilder
) : BuilderSupplier<KotlinAnnotationSpec, AnnotationSpec>, KotlinAnnotationSpecSupplier, DelegatingBuilder<KotlinAnnotationSpecBuilder, AnnotationSpecBuilderReceiver> {
  companion object {
    @JvmStatic
    fun builder(
      type: ClassName
    ): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    @JvmStatic
    fun builder(
      type: ParameterizedTypeName
    ): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    @JvmStatic
    fun builder(
      type: KClass<out Annotation>
    ): KotlinAnnotationSpecBuilder = builder(type.asClassName())
  }

  fun addMember(format: String, vararg args: Any): KotlinAnnotationSpecBuilder = apply {
    TODO() //delegate.addMember(format, *args)
  }

  fun addMember(codeBlock: CodeBlock): KotlinAnnotationSpecBuilder = apply {
    TODO() //delegate.addMember(codeBlock)
  }

  fun addKClassMember(name: String, klass: KClass<*>) = addMember("$name = %T::class", klass)

  fun addStringMember(name: String, value: String) = addMember("$name = %S", value)

  override fun builder(block: AnnotationSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(spec = delegate.build())
  override fun spec(): KotlinAnnotationSpec = build()
  override fun get(): AnnotationSpec = build().get()
}
