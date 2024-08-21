package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.TestFixtures
import io.toolisticon.kotlin.generation.support.GeneratedAnnotation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
internal class KotlinDataClassTest {

  @Test
  fun `create foo`() {
    val className = ClassName("test", "Foo")

    val spec = buildDataClass(className) {

      addKdoc("This is the data class documentation.")

      addAnnotation(GeneratedAnnotation(date = TestFixtures.NOW).comment("version" to "1.2"))
      addConstructorProperty("x", Int::class) {
        addKdoc("the x parameter")
      }

      addConstructorProperty("y", Long::class) {
        makePrivate()
        addAnnotation(TestFixtures.myAnnotationSpec)
        addKdoc("the y parameter")
      }
    }

    assertThat(spec.code.trim()).isEqualTo(
      """
      /**
       * This is the data class documentation.
       *
       * @param x the x parameter
       * @param y the y parameter
       */
      @jakarta.`annotation`.Generated(
        value = ["io.toolisticon.kotlin.generation.KotlinCodeGeneration"],
        date = "2024-07-02T10:01:33.205357100Z",
        comments = "version = 1.2",
      )
      public data class Foo(
        /**
         * the x parameter
         */
        public val x: kotlin.Int,
        /**
         * the y parameter
         */
        @io.toolisticon.kotlin.generation.TestFixtures.MyAnnotation
        private val y: kotlin.Long,
      )
    """.trimIndent()
    )
  }
}
