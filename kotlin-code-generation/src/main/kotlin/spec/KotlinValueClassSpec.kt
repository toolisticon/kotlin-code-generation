package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isValueClass
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

data class KotlinValueClassSpec(
  val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinValueClassSpec>, KotlinValueClassSpecSupplier {

  init {
    require(spec.isValueClass) { "Not a valueClass spec: $spec." }
  }

  override fun spec(): KotlinValueClassSpec = this
  override fun get(): TypeSpec = spec
}

// fun KotlinValueClassSpec.toBuilder() = KotlinValueClassBuilder.builder(spec = this)
