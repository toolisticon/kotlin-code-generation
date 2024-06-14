package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.KModifier
import io.toolisticon.kotlin.generation.BuilderSupplier

@Suppress("UNCHECKED_CAST")
sealed interface KModifierHolderBuilder<SELF : KModifierHolderBuilder<SELF, S, B>, S : Any, B : Any>
  : BuilderSupplier<S, B> {
  val modifiers: MutableList<KModifier>

  fun addModifiers(vararg modifiers: KModifier): SELF = apply {
    this.modifiers += modifiers
  } as SELF

  public fun addModifiers(modifiers: Iterable<KModifier>): SELF = apply {
    this.modifiers += modifiers
  } as SELF

}
