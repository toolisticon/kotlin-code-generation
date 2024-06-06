package io.toolisticon.kotlin.generation

fun interface Builder<P : Any> {
  fun build(): P
}
