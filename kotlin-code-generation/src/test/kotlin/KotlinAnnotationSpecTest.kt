package io.toolisticon.kotlin.generation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KotlinAnnotationSpecTest {

  annotation class Foo(
    val bar : String
  )

  @Test
  fun `create Foo annotation`() {
    val spec = KotlinCodeGeneration.annotationBuilder(Foo::class)
      .addMember("bar = %S", "hello world")
      .build()

    assertThat(spec.code).isEqualTo("""@io.toolisticon.kotlin.generation.KotlinAnnotationSpecTest.Foo(bar = "hello world")""")

    assertThat(spec).hasToString("""KotlinAnnotationSpec(typeName=io.toolisticon.kotlin.generation.KotlinAnnotationSpecTest.Foo, members=[bar = "hello world"])""")
  }
}
