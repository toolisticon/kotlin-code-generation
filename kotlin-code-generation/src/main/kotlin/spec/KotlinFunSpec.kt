package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.FunSpec

@JvmInline
value class KotlinFunSpec(private val spec: FunSpec) : KotlinPoetSpec<FunSpec>, FunSpecSupplier, Documentable by spec {
  override fun get(): FunSpec = spec


}
