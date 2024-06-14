package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec.Builder
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterizedTypeName
import io.toolisticon.kotlin.generation.BuilderSupplier
import kotlin.reflect.KClass

typealias AnnotationSpecBuilderReceiver = AnnotationSpecBuilder.() -> Unit

@JvmInline
value class AnnotationSpecBuilder(val builder: Builder) : BuilderSupplier<AnnotationSpec, Builder>,
    TaggableBuilder<AnnotationSpecBuilder, AnnotationSpec, Builder> {
  companion object {
    internal fun Builder.wrap() = AnnotationSpecBuilder(this)
    fun builder(type: ClassName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()
    fun builder(type: ParameterizedTypeName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()
    fun builder(type: KClass<out Annotation>): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()
  }

  val members: MutableList<CodeBlock> get() = get().members

  fun addMember(format: String, vararg args: Any): AnnotationSpecBuilder = apply { builder.addMember(format, *args) }
  fun addMember(codeBlock: CodeBlock): AnnotationSpecBuilder = apply { builder.addMember(codeBlock) }
  fun addMember(builder: CodeBlockBuilder): AnnotationSpecBuilder = addMember(builder.build())
  fun useSiteTarget(useSiteTarget: UseSiteTarget): AnnotationSpecBuilder = apply { builder.useSiteTarget(useSiteTarget) }

  override fun build(): AnnotationSpec = builder.build()
  override fun get(): Builder = builder
}
