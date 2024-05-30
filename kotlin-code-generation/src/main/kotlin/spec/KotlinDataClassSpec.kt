package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isDataClass
import io.toolisticon.kotlin.generation.builder.KotlinDataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinFileBuilder

data class KotlinDataClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinPoetNamedTypeSpec, DataClassSpecSupplier {

  init {
    require(spec.isDataClass) { "Not a dataClass spec: $spec." }
  }



  override fun get(): TypeSpec = spec
}

fun KotlinDataClassSpec.toBuilder() = KotlinDataClassBuilder.builder(spec = this)
fun KotlinDataClassSpec.toFileSpec() = KotlinFileBuilder.builder(this).build()
