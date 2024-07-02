package io.toolisticon.kotlin.generation

import java.util.function.Supplier


/**
 * Marks a type as capable of building a new product.
 */
fun interface Builder<PRODUCT : Any> {
  fun build(): PRODUCT
}

interface BuilderSupplier<P : Any, B : Any> : Builder<P>, Supplier<B>

