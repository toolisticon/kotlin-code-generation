package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec

@Deprecated("Not implemented yet!")
@JvmInline
value class KotlinAnonymousClassSpec(private val spec: TypeSpec) : KotlinPoetTypeSpec{
  override fun get(): TypeSpec = spec

}
