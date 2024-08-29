package io.toolisticon.kotlin.generation._test

import kotlin.reflect.KClass

data class TestInput(
  val map: MutableMap<String, KClass<*>> = mutableMapOf(),
) {
  fun put(key: String, value: KClass<*>) = apply { map[key] = value }
}
