package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.KotlinInterfaceSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinInterfaceBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinInterfaceSpec>(
  delegate = delegate
), TypeSpecSupplier {

  override fun build(): KotlinInterfaceSpec {
    TODO("Not yet implemented")
  }
}
