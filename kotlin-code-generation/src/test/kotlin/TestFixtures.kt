package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import kotlin.reflect.KClass

object TestFixtures {

  @Target(AnnotationTarget.VALUE_PARAMETER)
  annotation class MyAnnotation(
    val name: String,
    val type: KClass<*>
  )

  val myAnnotationSpec = buildAnnotation(MyAnnotation::class)

}
