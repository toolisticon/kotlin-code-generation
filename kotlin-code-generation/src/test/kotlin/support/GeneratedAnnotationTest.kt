
package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.TestFixtures
import io.toolisticon.kotlin.generation.spec.toFileSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class GeneratedAnnotationTest {

  @Test
  fun `generated annotation`() {
    val annotation = GeneratedAnnotation(
      date = TestFixtures.NOW
    ).comment("version" to "1.0").spec()

    assertThat(annotation.code).isEqualTo("""@jakarta.`annotation`.Generated(value = ["io.toolisticon.kotlin.generation.KotlinCodeGeneration"], date = "2024-07-02T10:01:33.205357100Z", comments = "version = 1.0")""")
  }

  @Test
  fun `apply generated to class`() {
    val file = KotlinCodeGeneration.buildDataClass("foo","Bar") {
      addAnnotation(GeneratedAnnotation(date = TestFixtures.NOW))
      addConstructorProperty("x", String::class)
    }.toFileSpec()

    assertThat(file.code).isEqualTo("""
      package foo

      import jakarta.`annotation`.Generated
      import kotlin.String

      @Generated(value = ["io.toolisticon.kotlin.generation.KotlinCodeGeneration"], date =
          "2024-07-02T10:01:33.205357100Z")
      public data class Bar(
        public val x: String,
      )

    """.trimIndent())
  }
}
