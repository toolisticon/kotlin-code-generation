package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget
import com.squareup.kotlinpoet.CodeBlock
import io.toolisticon.kotlin.generation.BuilderSupplier

@JvmInline
value class AnnotationSpecBuilder(val builder: AnnotationSpec.Builder) : BuilderSupplier<AnnotationSpec, AnnotationSpec.Builder>,
  TaggableBuilder<AnnotationSpecBuilder, AnnotationSpec, AnnotationSpec.Builder> {

  val members: MutableList<CodeBlock> get() = get().members

  fun addMember(format: String, vararg args: Any): AnnotationSpecBuilder = apply {
    builder.addMember(format, *args)
  }

  fun addMember(codeBlock: CodeBlock): AnnotationSpecBuilder = apply {
    builder.addMember(codeBlock)
  }

  fun addMember(builder: CodeBlockBuilder): AnnotationSpecBuilder = addMember(builder.build())

  fun useSiteTarget(useSiteTarget: UseSiteTarget): AnnotationSpecBuilder = apply {
    builder.useSiteTarget(useSiteTarget)
  }

  override fun build(): AnnotationSpec = builder.build()
  override fun get(): AnnotationSpec.Builder = builder
}
