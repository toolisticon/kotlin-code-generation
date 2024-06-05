package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.bak.ToKotlinPoetSpecBuilder
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilder
import io.toolisticon.kotlin.generation.spec.FunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFunSpec

class KotlinFunSpecBuilder internal constructor(
  private val delegate: FunSpecBuilder
) : Builder<KotlinFunSpec>, FunSpecSupplier {

  companion object {
    fun builder(name: String) = KotlinFunSpecBuilder(FunSpecBuilder.builder(name))
  }

  operator fun invoke(block: FunSpecBuilder.() -> Unit): KotlinFunSpecBuilder = apply {
    delegate.block()
  }

  override fun build(): KotlinFunSpec = KotlinFunSpec(spec = delegate.build())
  override fun get(): FunSpec = build().get()
}
