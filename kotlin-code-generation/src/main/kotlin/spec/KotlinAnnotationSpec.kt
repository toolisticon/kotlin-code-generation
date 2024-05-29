package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.*

@JvmInline
value class KotlinAnnotationSpec(override val spec: AnnotationSpec) : KotlinPoetSpec<AnnotationSpec>, AnnotationSpecSupplier {

  val typeName: TypeName get() = spec.typeName
  val members: List<CodeBlock> get() = spec.members

  override fun get(): AnnotationSpec = spec

  override fun toString(): String {
    return "KotlinAnnotationSpec(typeName=$typeName, members=$members)"
  }
}
