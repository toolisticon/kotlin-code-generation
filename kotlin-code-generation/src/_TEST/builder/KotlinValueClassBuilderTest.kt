package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildValueClass
import io.toolisticon.kotlin.generation.TestFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinValueClassBuilderTest {

  @Test
  fun `create foo value class`() {
    val className = ClassName("io.acme","Foo")

    val spec = buildValueClass(className) {
      primaryConstructor(KotlinConstructorPropertyBuilder.builder("bar", String::class.asTypeName())
        .addAnnotation(TestFixtures.myAnnotationSpec.get())
        .build())
    }

    assertThat(spec.code.trim()).isEqualTo("""
      @kotlin.jvm.JvmInline
      public value class Foo(
        @io.toolisticon.kotlin.generation.TestFixtures.MyAnnotation
        public val bar: kotlin.String,
      )
      """.trimIndent())
  }
}
