package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.annotation.GeneratedAnnotation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinDataClassTest {

  @Test
  fun `create foo`() {
    val className = ClassName("test", "Foo")

    val c1 = buildConstructorProperty("x", Int::class.asTypeName())
    val c2 = buildConstructorProperty("y", Long::class) {
      makePrivate()
    }

    val spec = buildDataClass(className) {
      addAnnotation(GeneratedAnnotation(date = TestFixtures.NOW.toString()).comment("version" to "1.2"))
      addConstructorProperty("x", Int::class.asTypeName())
      addConstructorProperty("y", Long::class) {
        makePrivate()
        addAnnotation(TestFixtures.myAnnotationSpec)
      }
    }

    assertThat(spec.code.trim()).isEqualTo(
      """
      @jakarta.`annotation`.Generated(
        value = "io.toolisticon.kotlin.generation.KotlinCodeGeneration",
        date = "2024-07-02T10:01:33.205357100Z",
        comments = "version = 1.2",
      )
      public data class Foo(
        public val x: kotlin.Int,
        @io.toolisticon.kotlin.generation.TestFixtures.MyAnnotation
        private val y: kotlin.Long,
      )
    """.trimIndent()
    )
  }
}
