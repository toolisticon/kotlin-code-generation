package io.toolisticon.kotlin.generation._BAK.bak

import io.toolisticon.kotlin.generation._BAK.KotlinPoetSpec

/**
 * Self-Type implementation of the root Spec builder. This is needed, because: a) we want implementing builders
 * to return an instance of themselves, so we can type-safe and fluently build products.
 * And b) because unfortunately kotlin-poet does not provide common super-types for specs and builders.
 *
 * @param SELF concrete instance of this builder to return in implementing classes.
 * @param PRODUCT the [KotlinPoetSpec] type returned by this builder.
 * @param SPEC the kotlin poet spec the [PRODUCT] refers to.
 * @param SPEC_BUILDER the specific kotlin poet builder used to modify [PRODUCT].
 * @param delegate provide an instance of the specific builder
 */
@Deprecated("remove")
sealed class BAK_KotlinPoetSpecBuilder<
  SELF : BAK_KotlinPoetSpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>,
  PRODUCT : KotlinPoetSpec<SPEC>,
  SPEC : Any,
  SPEC_BUILDER : Any
  >(
  protected val delegate: SPEC_BUILDER
) : SpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER> {

  @Suppress("UNCHECKED_CAST")
  protected fun applySelf(block: SELF.() -> Unit): SELF = (this as SELF).apply {
    block()
  }

  /**
   * Directly invoke underlying builder to support all build-in features.
   */
  operator fun invoke(block: SPEC_BUILDER.() -> Unit): SELF = applySelf {
    delegate.block()
  }

  abstract override fun build(): PRODUCT
}
