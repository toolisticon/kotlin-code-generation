package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.bak.BAK_KotlinPoetTypeSpecBuilder
import io.toolisticon.kotlin.generation.builder.bak.ToKotlinPoetTypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

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
