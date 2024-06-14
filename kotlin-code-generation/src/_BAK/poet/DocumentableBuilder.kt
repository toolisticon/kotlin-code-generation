package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.Documentable
import io.toolisticon.kotlin.generation.BuilderSupplier

sealed interface DocumentableBuilder<SELF : DocumentableBuilder<SELF, S, B>, S : Documentable, B : Documentable.Builder<B>>
  : BuilderSupplier<S, B>, Documentable.Builder<SELF> {
  override val kdoc: CodeBlock.Builder get() = get().kdoc

  fun addKdoc(builder: CodeBlockBuilder): SELF = addKdoc(builder.build())
}
