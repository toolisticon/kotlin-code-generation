package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeSpec

@JvmInline
value class KotlinInterfaceSpec(private val spec: TypeSpec) : KotlinPoetTypeSpec, Documentable by spec {
  override fun get(): TypeSpec = spec
}
