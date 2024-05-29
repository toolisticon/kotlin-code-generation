package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec



@JvmInline
value class KotlinObjectSpec(private val spec: TypeSpec) : KotlinPoetTypeSpec {
  override fun get(): TypeSpec = spec
}
