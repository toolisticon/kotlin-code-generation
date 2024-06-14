package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation._BAK.KotlinAnonymousClassSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier

@Deprecated("not implemented yet")
class KotlinAnonymousClassBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : Builder<KotlinAnonymousClassSpec>, TypeSpecSupplier {


  override fun build(): KotlinAnonymousClassSpec {
    TODO("Not yet implemented")
  }

  override fun get(): TypeSpec {
    TODO("Not yet implemented")
  }
}
