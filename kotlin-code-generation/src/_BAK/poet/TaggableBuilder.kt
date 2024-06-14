package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.Taggable
import io.toolisticon.kotlin.generation.BuilderSupplier
import kotlin.reflect.KClass

sealed interface TaggableBuilder<SELF : TaggableBuilder<SELF, S, B>, S : Taggable, B : Taggable.Builder<B>>
  : BuilderSupplier<S, B>, Taggable.Builder<SELF> {
  override val tags: MutableMap<KClass<*>, Any> get() = get().tags
}
