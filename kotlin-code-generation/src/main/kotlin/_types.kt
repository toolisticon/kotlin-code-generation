package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.poet.PoetSpecSupplier
import io.toolisticon.kotlin.generation.spec.*


/**
 * Marks a type as capable of building a new product.
 */
fun interface Builder<PRODUCT : Any> {
  fun build(): PRODUCT
}


