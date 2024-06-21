package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.poet.FunSpecSupplier

data class KotlinFunSpec(
  private val spec: FunSpec
) : KotlinGeneratorSpec<KotlinFunSpec, FunSpec, FunSpecSupplier>, KotlinFunSpecSupplier {
  override fun spec(): KotlinFunSpec = this
  override fun get(): FunSpec = spec
}
interface KotlinFunSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFunSpec>, FunSpecSupplier
