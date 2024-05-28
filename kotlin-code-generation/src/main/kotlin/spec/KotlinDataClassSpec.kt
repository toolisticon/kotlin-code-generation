package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.builder.KotlinFileBuilder

data class KotlinDataClassSpec(
  val className: ClassName,
  override val spec: TypeSpec
) : KotlinPoetTypeSpec, DataClassSpecSupplier {

  fun toFileSpec() = KotlinFileBuilder.builder(this).build()
}
