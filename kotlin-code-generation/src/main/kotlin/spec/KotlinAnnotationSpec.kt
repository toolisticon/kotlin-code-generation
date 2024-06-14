package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.AnnotationSpecSupplier

data class KotlinAnnotationSpec(
  private val spec: AnnotationSpec
) : AnnotationSpecSupplier {

  companion object {
    fun of(annotations: List<AnnotationSpec>): List<KotlinAnnotationSpec> = annotations.map { KotlinAnnotationSpec(it) }
  }

  val typeName: TypeName get() = spec.typeName
  val members: List<CodeBlock> get() = spec.members

  override fun get(): AnnotationSpec = spec

  override fun toString(): String {
    return "KotlinAnnotationSpec(typeName=$typeName, members=$members)"
  }

}

// TODO fun KotlinAnnotationSpec.toBuilder() = KotlinAnnotationSpecBuilder.from(spec = this)
