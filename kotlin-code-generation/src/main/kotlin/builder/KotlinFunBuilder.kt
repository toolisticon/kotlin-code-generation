package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.FunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFunSpec

@Deprecated("Not implemented yet!")
class KotlinFunBuilder internal constructor(delegate: FunSpec.Builder) : KotlinPoetSpecBuilder<KotlinFunBuilder, KotlinFunSpec, FunSpec, FunSpec.Builder>(
  delegate = delegate
), FunSpecSupplier {


  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetSpecBuilder<KotlinFunSpec, KotlinFunBuilder> {
    override fun invoke(spec: KotlinFunSpec): KotlinFunBuilder = KotlinFunBuilder(spec.get().toBuilder())
  }


  override fun build(): KotlinFunSpec = KotlinFunSpec(spec = delegate.build())

  override fun get(): FunSpec = build().get()
}
