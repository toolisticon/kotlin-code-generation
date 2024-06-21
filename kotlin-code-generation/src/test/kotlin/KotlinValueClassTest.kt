package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildValueClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.toFileSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinValueClassTest {

  @Test
  fun `build foo with string value`() {
    val className = ClassName("test", "Foo")
    val valueClass = buildValueClass(className = className) {
      primaryConstructor(buildConstructorProperty("bar", String::class.asTypeName()) {
        makePrivate()
        addAnnotation(TestFixtures.myAnnotationSpec)
      })
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
}
