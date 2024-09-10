package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.poet.KDoc
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
data class KotlinInterfaceSpec(
  override val className: ClassName,
  private val spec: TypeSpec
) : KotlinGeneratorTypeSpec<KotlinInterfaceSpec>, KotlinInterfaceSpecSupplier, KotlinDocumentableSpec {

  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val kdoc: KDoc get() = KDoc(spec.kdoc)
  override fun spec(): KotlinInterfaceSpec = this
  override fun get(): TypeSpec = spec
}

@ExperimentalKotlinPoetApi
interface KotlinInterfaceSpecSupplier : KotlinGeneratorSpecSupplier<KotlinInterfaceSpec>, ToFileTypeSpecSupplier {
  override fun get(): TypeSpec = spec().get()
}
