package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotationClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFile
import io.toolisticon.kotlin.generation.itest.KotlinCodeGenerationITest.ROOT_PACKAGE

object MyCustomAnnotationSpec {
  val name = ClassName(ROOT_PACKAGE, "MyCustomAnnotation")

  val spec = buildAnnotationClass(name) {
    mustBeDocumented()
    retention(AnnotationRetention.RUNTIME)
    target(AnnotationTarget.CLASS)
    addConstructorProperty("value", String::class)
  }

  val file = buildFile(name) {
    addType(spec)
  }
}
