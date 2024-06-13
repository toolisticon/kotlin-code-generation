package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec

@JvmInline
value class KotlinClassSpec(private val spec: TypeSpec) : KotlinPoetTypeSpec {
  override fun get(): TypeSpec = spec
}
