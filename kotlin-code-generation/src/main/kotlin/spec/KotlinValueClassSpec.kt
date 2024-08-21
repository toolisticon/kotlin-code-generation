package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.typeSpec.isValueClass
import io.toolisticon.kotlin.generation.WithClassName
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier

@ExperimentalKotlinPoetApi
data class KotlinValueClassSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinValueClassSpec>, KotlinValueClassSpecSupplier, KotlinDocumentableSpec {

  init {
    require(spec.isValueClass) { "Not a valueClass spec: $spec." }
  }

  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinValueClassSpec = this
  override fun get(): TypeSpec = spec
}

// fun KotlinValueClassSpec.toBuilder() = KotlinValueClassBuilder.builder(spec = this)
@ExperimentalKotlinPoetApi
interface KotlinValueClassSpecSupplier : KotlinGeneratorSpecSupplier<KotlinValueClassSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
