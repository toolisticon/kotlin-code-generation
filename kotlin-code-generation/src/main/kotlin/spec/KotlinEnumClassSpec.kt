package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeSpec

@JvmInline
value class KotlinEnumClassSpec(private val spec: TypeSpec) : KotlinPoetTypeSpec, Documentable by spec {

  init {
      require(spec.isEnum) { "Not an enum spec: $spec" }
  }

  override fun get(): TypeSpec = spec
}
