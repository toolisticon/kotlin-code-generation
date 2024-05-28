package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.FunSpec

@Deprecated("Not implemented yet!")
@JvmInline
value class KotlinFunSpec(override val spec: FunSpec) : KotlinPoetSpec<FunSpec>, FunSpecSupplier{
  override fun get(): FunSpec = spec
}
