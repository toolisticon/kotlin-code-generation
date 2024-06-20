package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation

object TestFixtures {

  @Target(AnnotationTarget.VALUE_PARAMETER)
  annotation class MyAnnotation()

  val myAnnotationSpec = buildAnnotation(MyAnnotation::class)

}
