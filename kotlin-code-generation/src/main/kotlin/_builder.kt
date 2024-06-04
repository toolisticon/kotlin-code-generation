package io.toolisticon.kotlin.generation

import java.util.function.Supplier

fun interface Builder<P : Any> {
  fun build(): P
}

interface BuilderSupplier<P : Any, B : Any> : Builder<P>, Supplier<B>
