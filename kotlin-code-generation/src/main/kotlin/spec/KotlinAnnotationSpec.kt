package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationBuilder

@JvmInline
value class KotlinAnnotationSpec(private val spec: AnnotationSpec) : KotlinPoetSpec<AnnotationSpec>,
  AnnotationSpecSupplier, WithTypeName {

  companion object {
    fun of(annotations: List<AnnotationSpec>): List<KotlinAnnotationSpec> = annotations.map { KotlinAnnotationSpec(it) }
  }

  override val typeName: TypeName get() = spec.typeName
  val members: List<CodeBlock> get() = spec.members

  override fun get(): AnnotationSpec = spec

  override fun toString(): String {
    return "KotlinAnnotationSpec(typeName=$typeName, members=$members)"
  }

}

fun KotlinAnnotationSpec.toBuilder() = KotlinAnnotationBuilder.builder(spec = this)
