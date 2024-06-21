package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationSpecBuilder
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier

data class KotlinAnnotationSpec(
  private val spec: AnnotationSpec
) : KotlinGeneratorSpec<KotlinAnnotationSpec, AnnotationSpec, AnnotationSpecSupplier>, KotlinAnnotationSpecSupplier {

  companion object {
    fun of(annotations: List<AnnotationSpec>): List<KotlinAnnotationSpec> = annotations.map { KotlinAnnotationSpec(it) }
  }

  val typeName: TypeName get() = spec.typeName
  val members: List<CodeBlock> get() = spec.members

  override fun get(): AnnotationSpec = spec
  override fun spec(): KotlinAnnotationSpec = this

  override fun toString(): String {
    return "KotlinAnnotationSpec(typeName=$typeName, members=$members)"
  }

}

interface KotlinAnnotationSpecSupplier : KotlinGeneratorSpecSupplier<KotlinAnnotationSpec>, AnnotationSpecSupplier

fun KotlinAnnotationSpec.toBuilder() = KotlinAnnotationSpecBuilder.from(spec = this)
