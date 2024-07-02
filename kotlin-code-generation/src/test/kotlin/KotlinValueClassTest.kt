package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildValueClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.toFileSpec
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinValueClassTest {

  @Test
  fun `build foo with string value`() {
    val className = ClassName("test", "Foo")
    val valueClass = buildValueClass(className = className) {
      primaryConstructor("bar", String::class) {
        makePrivate()
        addAnnotation(TestFixtures.myAnnotationSpec)
      }
    }

    assertThat(toFileSpec(valueClass).code.trim()).isEqualTo(
      """package test

import io.toolisticon.kotlin.generation.TestFixtures
import kotlin.String
import kotlin.jvm.JvmInline

@JvmInline
public value class Foo(
  @TestFixtures.MyAnnotation
  private val bar: String,
)"""
    )
  }


  @Test
  fun `create foo value class`() {
    val className = ClassName("io.acme", "Foo")

    val spec = buildValueClass(className) {
      primaryConstructor(
        KotlinConstructorPropertySpecBuilder.builder("bar", String::class.asTypeName())
          .addAnnotation(TestFixtures.myAnnotationSpec)
          .build()
      )
    }

    assertThat(spec.code.trim()).isEqualTo(
      """
      @kotlin.jvm.JvmInline
      public value class Foo(
        @io.toolisticon.kotlin.generation.TestFixtures.MyAnnotation
        public val bar: kotlin.String,
      )
      """.trimIndent()
    )
  }
}
