package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.asClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation

object TestFixtures {

  @Target(AnnotationTarget.VALUE_PARAMETER)
  annotation class MyAnnotation()

  val myAnnotationSpec = buildAnnotation(MyAnnotation::class.asClassName()) {}

}
