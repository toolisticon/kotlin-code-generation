package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier

sealed interface TypeSpecHolderBuilder<SELF : TypeSpecHolderBuilder<SELF, S, B>, S : TypeSpecHolder, B : TypeSpecHolder.Builder<B>>
  : BuilderSupplier<S, B>, TypeSpecHolder.Builder<SELF> {
  override fun addType(typeSpec: TypeSpec): SELF

  @OptIn(ExperimentalKotlinPoetApi::class)
  fun addType(builder: TypeSpecBuilder): SELF = addType(builder.build())
}
