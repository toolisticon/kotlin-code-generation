package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.FunSpec

@JvmInline
value class KotlinConstructorSpec(private val spec: FunSpec) : KotlinPoetSpec<FunSpec> {
  override fun get(): FunSpec = spec
}
