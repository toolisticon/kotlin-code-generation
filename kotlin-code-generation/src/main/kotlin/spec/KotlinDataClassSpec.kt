package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.builder.KotlinDataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinFileBuilder

data class KotlinDataClassSpec(
  val className: ClassName,
  private val spec: TypeSpec
) : KotlinPoetTypeSpec, DataClassSpecSupplier {

  init {
    require(spec.isDataClass) { "Nat a dataClass spec: $spec." }
  }

  fun toFileSpec() = KotlinFileBuilder.builder(this).build()

  override fun get(): TypeSpec = spec
}

fun KotlinDataClassSpec.toBuilder() = KotlinDataClassBuilder.builder(spec = this)
