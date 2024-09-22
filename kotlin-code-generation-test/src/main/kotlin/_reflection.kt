package io.toolisticon.kotlin.generation.test

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor


/**
 * Create instance of type `<T>` from [KClass].
 */
inline fun <reified T : Any> KClass<out Any>.callPrimaryConstructor(vararg params: Any): T {
  // TODO "call" is not resolvable
  return if (params.isEmpty()) {
    this.createInstance()
  } else {
    val pc = requireNotNull(this.primaryConstructor)
    pc.call(*params)
  } as T
}

/**
 * Get value of type `T` of field.
 *
 * @param name name of the field
 * @return value T
 */
// TODO looking for a pure kotlin reflect solution
inline fun <reified T : Any> Any.getFieldValue(name: String): T = this::class.java
  .getDeclaredField(name)
  .apply { isAccessible = true }
  .get(this) as T
