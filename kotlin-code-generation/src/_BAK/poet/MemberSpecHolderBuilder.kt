package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberSpecHolder
import com.squareup.kotlinpoet.PropertySpec
import io.toolisticon.kotlin.generation.BuilderSupplier

sealed interface MemberSpecHolderBuilder<SELF : MemberSpecHolderBuilder<SELF, S, B>, S : MemberSpecHolder, B : MemberSpecHolder.Builder<B>>
  : BuilderSupplier<S, B>, MemberSpecHolder.Builder<SELF> {
  override fun addProperty(propertySpec: PropertySpec): SELF

  @OptIn(ExperimentalKotlinPoetApi::class)
  fun addProperty(builder: PropertySpecBuilder): SELF = addProperty(builder.build())

  override fun addFunction(funSpec: FunSpec): SELF

  @OptIn(ExperimentalKotlinPoetApi::class)
  fun addFunction(builder: FunSpecBuilder): SELF = addFunction(builder.build())
}
