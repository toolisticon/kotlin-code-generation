@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.TestFixtures.MyAnnotation
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationSpecBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass


internal class KotlinAnnotationTest {

  annotation class Foo(
    val bar: String,
    val x: String = "",
    val type: KClass<*> = String::class
  )

  @Test
  fun buildMyAnnotation() {
    val annotation = buildAnnotation(MyAnnotation::class) {
      addStringMember("name", "foo")
      addKClassMember("type", String::class)
    }

    assertThat(annotation.code).isEqualTo("""@io.toolisticon.kotlin.generation.TestFixtures.MyAnnotation(name = "foo", type = kotlin.String::class)""");
  }


  @Test
  fun `create Foo annotation`() {
    val spec = KotlinAnnotationSpecBuilder.builder(Foo::class)
      .addMember("bar = %S", "hello world")
      .addStringMember("x", "foo")
      .addKClassMember("type", Long::class)
      .build()

    assertThat(spec.code).isEqualTo("""@io.toolisticon.kotlin.generation.spec.KotlinAnnotationTest.Foo(bar = "hello world", x = "foo", type = kotlin.Long::class)""")
    assertThat(spec.members).hasSize(3)

    assertThat(spec).hasToString(
      "KotlinAnnotationSpec(" +
        "typeName=io.toolisticon.kotlin.generation.spec.KotlinAnnotationTest.Foo, " +
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

  @Test
  fun `create target annotation`() {
    val annotation = buildAnnotation(Target::class) {
      addEnumMembers("allowedTargets", AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FILE)
    }

    println(annotation.code)
  }
}
