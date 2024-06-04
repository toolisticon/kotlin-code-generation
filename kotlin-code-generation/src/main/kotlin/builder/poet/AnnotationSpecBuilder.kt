package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget
import com.squareup.kotlinpoet.CodeBlock

@JvmInline
value class AnnotationSpecBuilder(val builder: AnnotationSpec.Builder) : KotlinPoetBuilderSupplier<AnnotationSpec, AnnotationSpec.Builder>,
  TaggableBuilder<AnnotationSpec, AnnotationSpec.Builder> {

  val members: MutableList<CodeBlock> get() = get().members

  fun addMember(format: String, vararg args: Any): AnnotationSpecBuilder = apply {
    addMember(CodeBlock.of(format, *args))
  }

  fun addMember(codeBlock: CodeBlock): AnnotationSpecBuilder = apply {
    builder.addMember(codeBlock)
  }

  fun useSiteTarget(useSiteTarget: UseSiteTarget?): AnnotationSpecBuilder = apply {
    builder.useSiteTarget(useSiteTarget)
  }

  override fun get(): AnnotationSpec.Builder  = builder
  override fun build(): AnnotationSpec = builder.build()
}
