package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget
import kotlin.reflect.KClass

class AnnotationSpecBuilder(
  override val builder: AnnotationSpec.Builder
) : PoetSpecBuilder<AnnotationSpecBuilder, AnnotationSpec.Builder, AnnotationSpec, AnnotationSpecSupplier> {
  companion object {
    fun AnnotationSpec.Builder.wrap() = AnnotationSpecBuilder(this)

    @JvmStatic
    fun builder(type: ClassName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()

    @JvmStatic
    fun builder(type: ParameterizedTypeName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()


    @JvmStatic
    fun builder(type: KClass<out Annotation>): AnnotationSpecBuilder = builder(type.asClassName())
  }

  // Taggable
  fun tag(type: KClass<*>, tag: Any?) = invoke { tag(type, tag) }

  // Annotatable
  fun addMember(format: String, vararg args: Any) = invoke { addMember(CodeBlock.of(format, *args)) }
  fun addMember(codeBlock: CodeBlock) = invoke { addMember(codeBlock) }
  fun useSiteTarget(useSiteTarget: UseSiteTarget?) = invoke { useSiteTarget(useSiteTarget) }

  override fun invoke(block: AnnotationSpecBuilderReceiver): AnnotationSpecBuilder = apply {
    builder.block()
  }

  override fun build(): AnnotationSpec = builder.build()
}

interface AnnotationSpecSupplier : PoetSpecSupplier<AnnotationSpec>
typealias AnnotationSpecBuilderReceiver = AnnotationSpec.Builder.() -> Unit
