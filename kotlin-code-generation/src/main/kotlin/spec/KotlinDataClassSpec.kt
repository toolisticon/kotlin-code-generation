package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isDataClass
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

data class KotlinDataClassSpec(
  val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinDataClassSpec>, KotlinDataClassSpecSupplier {

  init {
    require(spec.isDataClass) { "Not a dataClass spec: $spec." }
  }

  override fun spec(): KotlinDataClassSpec = this
  override fun get(): TypeSpec = spec
}

//fun KotlinDataClassSpec.toBuilder() = KotlinDataClassSpecBuilder.builder(spec = this)
//fun KotlinDataClassSpec.toFileSpec() = KotlinFileBuilder.builder(this).build()
// TODO fun KotlinDataClassSpec.toBuilder() = KotlinDataClassBuilder.from(spec = this)
// TODO fun KotlinDataClassSpec.toFileSpec() = KotlinFileSpecBuilder.builder(this).build()
interface KotlinDataClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinDataClassSpec>, TypeSpecSupplier
