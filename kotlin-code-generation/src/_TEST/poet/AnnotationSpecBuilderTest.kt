package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.AnnotationSpec
import io.toolisticon.kotlin.generation._BAK.poet.AnnotationSpecBuilder
import org.junit.jupiter.api.Test

internal class AnnotationSpecBuilderTest {

  @Test
  fun name() {
    val b = AnnotationSpec.builder(Deprecated::class)
    val a = AnnotationSpecBuilder(b)


  }
}
