package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
data class KotlinCompanionObjectSpec(
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinCompanionObjectSpec>, KotlinCompanionObjectSpecSupplier, KotlinDocumentableSpec {

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinCompanionObjectSpec = this
  override fun get(): TypeSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinCompanionObjectSpecSupplier : KotlinGeneratorSpecSupplier<KotlinCompanionObjectSpec>, TypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
