package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.OriginatingElementsHolder
import io.toolisticon.kotlin.generation.BuilderSupplier
import javax.lang.model.element.Element

sealed interface OriginatingElementsHolderBuilder<SELF : OriginatingElementsHolderBuilder<SELF, S, B>, S : OriginatingElementsHolder, B : OriginatingElementsHolder.Builder<B>>
  : BuilderSupplier<S, B>, OriginatingElementsHolder.Builder<SELF> {
  override val originatingElements: MutableList<Element> get() = get().originatingElements
}
