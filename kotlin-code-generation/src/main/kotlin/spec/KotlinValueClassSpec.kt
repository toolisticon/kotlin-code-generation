package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isValueClass

data class KotlinValueClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinPoetNamedTypeSpec, Documentable by spec {

  init {
    require(spec.isValueClass) { "Not a valueClass spec: $spec." }
  }

  override fun get(): TypeSpec = spec

}

// fun KotlinValueClassSpec.toBuilder() = KotlinValueClassBuilder.builder(spec = this)
