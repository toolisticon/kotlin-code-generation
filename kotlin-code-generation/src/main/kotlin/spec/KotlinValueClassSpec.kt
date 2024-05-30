package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isValueClass
import io.toolisticon.kotlin.generation.builder.KotlinValueClassBuilder

data class KotlinValueClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinPoetNamedTypeSpec {

  init {
    require(spec.isValueClass) { "Not a valueClass spec: $spec." }
  }

  override fun get(): TypeSpec = spec

}

fun KotlinValueClassSpec.toBuilder() = KotlinValueClassBuilder.builder(spec = this)
