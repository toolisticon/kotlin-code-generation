package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.builder.KotlinAnnotationSpecBuilder
import io.toolisticon.kotlin.generation._BAK.toBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class KotlinAnnotationSpecTest {

  annotation class Foo(
    val bar: String,
    val x: String = "",
    val type: KClass<*> = String::class
  )

  @Test
  fun `create Foo annotation`() {
    val spec = KotlinAnnotationSpecBuilder.builder(Foo::class)
      .addMember("bar = %S", "hello world")
      .addStringMember("x", "foo")
      .addKClassMember("type", Long::class)
      .build()

    assertThat(spec.code).isEqualTo("""@io.toolisticon.kotlin.generation.KotlinAnnotationSpecTest.Foo(bar = "hello world", x = "foo", type = kotlin.Long::class)""")
    assertThat(spec.members).hasSize(3)

    assertThat(spec).hasToString(
      "KotlinAnnotationSpec(" +
        "typeName=io.toolisticon.kotlin.generation.KotlinAnnotationSpecTest.Foo, " +
        "members=[" +
        "bar = \"hello world\", " +
        "x = \"foo\", " +
        "type = kotlin.Long::class]" +
        ")"
    )
  }

  @Test
  fun `use toBuilder to add members`() {
    var spec = KotlinAnnotationSpecBuilder.builder(Foo::class)
      .addMember("bar", "hello")
      .build()

    assertThat(spec.members).hasSize(1)

    spec = spec.toBuilder()
      .addMember("type", Int::class)
      .build()

    assertThat(spec.members).hasSize(2)
  }
}
