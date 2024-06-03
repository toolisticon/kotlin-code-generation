package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeSpec

data class KotlinAnnotationClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec,
) : KotlinPoetNamedTypeSpec, Documentable by spec {
  override fun get(): TypeSpec = spec
}
