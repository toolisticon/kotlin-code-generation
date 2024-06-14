package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation._BAK.KotlinCompanionObjectSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier

class KotlinCompanionObjectBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : Builder<KotlinCompanionObjectSpec>, TypeSpecSupplier {

  companion object {
    fun builder(name: String? = null) = KotlinCompanionObjectBuilder(
      delegate = TypeSpecBuilder.companionObjectBuilder(name)
    )
  }
  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinCompanionObjectBuilder = apply {
    delegate.block()
  }

  override fun build(): KotlinCompanionObjectSpec = KotlinCompanionObjectSpec(spec = delegate.build())
  override fun get(): TypeSpec = build().get()
}
