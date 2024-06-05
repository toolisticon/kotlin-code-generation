package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isDataClass
import io.toolisticon.kotlin.generation.builder.KotlinDataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder

data class KotlinDataClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinPoetNamedTypeSpec, DataClassSpecSupplier, Documentable by spec {

  init {
    require(spec.isDataClass) { "Not a dataClass spec: $spec." }
  }

  override fun get(): TypeSpec = spec
}

// TODO fun KotlinDataClassSpec.toBuilder() = KotlinDataClassBuilder.from(spec = this)
// TODO fun KotlinDataClassSpec.toFileSpec() = KotlinFileSpecBuilder.builder(this).build()
