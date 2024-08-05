package io.toolisticon.kotlin.generation.itest.spi

import com.squareup.kotlinpoet.ClassName
import kotlin.reflect.KClass

class MapInput(
  val className: ClassName,
  val fields: Map<String, KClass<*>>
) : Map<String, KClass<*>> by fields
