package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec

data class KotlinAnnotationClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec,
) : KotlinPoetNamedTypeSpec {
  override fun get(): TypeSpec = spec
}
