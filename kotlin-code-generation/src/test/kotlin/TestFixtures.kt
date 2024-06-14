package io.toolisticon.kotlin.generation

object TestFixtures {

  @Target(AnnotationTarget.VALUE_PARAMETER)
  annotation class MyAnnotation()

//  val myAnnotationSpec = buildAnnotation(MyAnnotation::class.asClassName()) {}

}
