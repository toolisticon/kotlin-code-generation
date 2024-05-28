package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.spec.FunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFunSpec

@Deprecated("Not implemented yet!")
class KotlinFunBuilder internal constructor(delegate: FunSpec.Builder) : KotlinPoetSpecBuilder<KotlinFunBuilder, KotlinFunSpec, FunSpec, FunSpec.Builder>(
  delegate = delegate
), FunSpecSupplier {

  override fun build(): KotlinFunSpec {
    TODO("Not yet implemented")
  }

  override fun get(): FunSpec = build().get()
}
