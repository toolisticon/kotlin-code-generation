package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.TypeSpec
import org.junit.jupiter.api.Test

internal class KotlinClassBuilderTest {

  @Test
  fun `kotlin poet only generation`() {
    val builder = TypeSpec.classBuilder("Bar")
      .addKdoc("%L", "hello world")

    println(builder.build())
  }
}
