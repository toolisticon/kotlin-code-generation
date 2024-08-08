package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

data class KotlinCompanionObjectSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinCompanionObjectSpec>, KotlinCompanionObjectSpecSupplier, KotlinDocumentableSpec {

  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinCompanionObjectSpec = this
  override fun get(): TypeSpec = spec
}

interface KotlinCompanionObjectSpecSupplier : KotlinGeneratorSpecSupplier<KotlinCompanionObjectSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
