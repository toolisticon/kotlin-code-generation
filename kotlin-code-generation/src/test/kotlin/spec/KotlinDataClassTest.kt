package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.TestFixtures
import io.toolisticon.kotlin.generation.support.GeneratedAnnotation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinDataClassTest {

  @Test
  fun `create foo`() {
    val className = ClassName("test", "Foo")

    val spec = buildDataClass(className) {
      addAnnotation(GeneratedAnnotation(date = TestFixtures.NOW).comment("version" to "1.2"))
      addConstructorProperty("x", Int::class)
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
