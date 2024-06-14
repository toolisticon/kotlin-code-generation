package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.ContextReceivable
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.BuilderSupplier

@ExperimentalKotlinPoetApi
sealed interface ContextReceivableBuilder<SELF : ContextReceivableBuilder<SELF, S, B>, S : ContextReceivable, B : ContextReceivable.Builder<B>>
  : BuilderSupplier<S, B>, ContextReceivable.Builder<SELF> {
  override val contextReceiverTypes: MutableList<TypeName> get() = get().contextReceiverTypes
}
