package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import java.time.Instant
import kotlin.reflect.KClass

object TestFixtures {

  // a fixed instant to be used in test with
  val NOW = Instant.parse( "2024-07-02T10:01:33.205357100Z")

  @Target(AnnotationTarget.VALUE_PARAMETER)
  annotation class MyAnnotation(
    val name: String,
    val type: KClass<*>
  )

  val myAnnotationSpec = buildAnnotation(MyAnnotation::class)

}
