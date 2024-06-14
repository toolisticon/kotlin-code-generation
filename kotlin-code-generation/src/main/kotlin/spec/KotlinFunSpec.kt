package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.FunSpecSupplier

data class KotlinFunSpec(
  private val spec: FunSpec
) : FunSpecSupplier {
  override fun get(): FunSpec = spec
}
