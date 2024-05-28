package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinAnonymousClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinAnonymousClassSpec>(
  delegate = delegate
), TypeSpecSupplier {

  override fun build(): KotlinAnonymousClassSpec {
    TODO("Not yet implemented")
  }
}
