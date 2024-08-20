package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.TestFixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
internal class GeneratedAnnotationTest {

  @Test
  fun `generated annotation`() {
    val annotation = GeneratedAnnotation(
      date = TestFixtures.NOW
    ).comment("version" to "1.0").spec()

    assertThat(annotation.code).isEqualTo("""@jakarta.`annotation`.Generated(value = "io.toolisticon.kotlin.generation.KotlinCodeGeneration", date = "2024-07-02T10:01:33.205357100Z", comments = "version = 1.0")""")
  }
}
