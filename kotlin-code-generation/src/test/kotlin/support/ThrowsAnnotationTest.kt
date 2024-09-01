package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class ThrowsAnnotationTest {

  @Test
  fun `create throw annotation with one type`() {
    val annotation = ThrowsAnnotation(RuntimeException::class)

    assertThat(annotation.spec().code).isEqualToIgnoringWhitespace("""
      @kotlin.jvm.Throws(java.lang.RuntimeException::class)
    """.trimIndent())
  }

  @Test
  fun `create throw annotation with two type`() {
    val annotation = ThrowsAnnotation(RuntimeException::class, NotImplementedError::class)

    assertThat(annotation.spec().code).isEqualToIgnoringWhitespace("""
      @kotlin.jvm.Throws(java.lang.RuntimeException::class, kotlin.NotImplementedError::class)
    """.trimIndent())
  }
}
