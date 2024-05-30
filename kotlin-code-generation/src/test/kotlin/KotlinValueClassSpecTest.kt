package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.valueClassBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinValueClassSpecTest {

  @Test
  fun `build foo with string value`() {
    val builder = valueClassBuilder("test","Foo")
      .primaryConstructor(
        constructorPropertyBuilder("bar", String::class.asClassName())
          .addAnnotation(TestFixtures.myAnnotationSpec)
      )


    val spec = builder.build()

    assertThat(spec.code.trim()).isEqualTo("""
      @kotlin.jvm.JvmInline
      public value class Foo(
        @io.toolisticon.kotlin.generation.TestFixtures.MyAnnotation
        public val bar: kotlin.String,
      )
      """.trimIndent())
  }
}
