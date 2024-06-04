package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.AnnotationSpec
import org.junit.jupiter.api.Test

internal class AnnotationSpecBuilderTest {

  @Test
  fun name() {
    val b = AnnotationSpec.builder(Deprecated::class)
    val a = AnnotationSpecBuilder(b)


  }
}
