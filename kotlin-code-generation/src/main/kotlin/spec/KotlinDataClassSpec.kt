package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.Documentable
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isDataClass
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinFileBuilder

data class KotlinDataClassSpec(
  val className: ClassName,
  private val spec: TypeSpec
) : TypeSpecSupplier {

  init {
    require(spec.isDataClass) { "Not a dataClass spec: $spec." }
  }

  override fun get(): TypeSpec = spec
}

//fun KotlinDataClassSpec.toBuilder() = KotlinDataClassSpecBuilder.builder(spec = this)
//fun KotlinDataClassSpec.toFileSpec() = KotlinFileBuilder.builder(this).build()
// TODO fun KotlinDataClassSpec.toBuilder() = KotlinDataClassBuilder.from(spec = this)
// TODO fun KotlinDataClassSpec.toFileSpec() = KotlinFileSpecBuilder.builder(this).build()
