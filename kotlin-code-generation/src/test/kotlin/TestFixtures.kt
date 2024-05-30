package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.KotlinCodeGeneration.annotationBuilder

object TestFixtures {

  @Target(AnnotationTarget.VALUE_PARAMETER)
  annotation class MyAnnotation()

  val myAnnotationSpec = annotationBuilder(type = MyAnnotation::class)

}
