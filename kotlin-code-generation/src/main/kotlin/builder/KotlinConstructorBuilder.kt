package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinConstructorSpec

class KotlinConstructorBuilder internal constructor(
  delegate: FunSpec.Builder
) : KotlinPoetSpecBuilder<KotlinConstructorBuilder, KotlinConstructorSpec, FunSpec, FunSpec.Builder>(
  delegate = delegate
) {

  @Suppress(CLASS_NAME)
  object builder {
    operator fun invoke(block: FunSpecBuilderReceiver = {}) = KotlinConstructorBuilder(
      delegate = FunSpec.constructorBuilder()
    ).invoke(block)
  }

  override fun build(): KotlinConstructorSpec {
    return KotlinConstructorSpec(spec = delegate.build())
  }
}
