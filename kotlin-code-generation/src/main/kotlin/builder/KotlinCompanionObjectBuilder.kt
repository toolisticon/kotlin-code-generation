package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.KotlinCompanionObjectSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinCompanionObjectBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinCompanionObjectSpec>(
  delegate = delegate
), TypeSpecSupplier {
  override fun build(): KotlinCompanionObjectSpec {
    TODO("Not yet implemented")
  }
}
