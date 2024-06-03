package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeSpec



@JvmInline
value class KotlinObjectSpec(private val spec: TypeSpec) : KotlinPoetTypeSpec, Documentable by spec {
  override fun get(): TypeSpec = spec
}
