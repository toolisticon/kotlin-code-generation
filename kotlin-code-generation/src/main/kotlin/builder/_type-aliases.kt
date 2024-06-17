package io.toolisticon.kotlin.generation.builder


interface DelegatingBuilder<SELF, RECEIVER> {
  fun builder(block: RECEIVER) : SELF
}
