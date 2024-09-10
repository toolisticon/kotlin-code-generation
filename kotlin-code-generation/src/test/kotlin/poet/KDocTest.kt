
package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


@OptIn(ExperimentalKotlinPoetApi::class)
internal class KDocTest {

  @Test
  fun `kdoc from vararg`() {
    val kdoc = KDoc.of("%L - %L", "Hello", "World")
    assertThat(kdoc).hasToString("Hello - World")
  }

  @Test
  fun `kdoc from string`() {
    val kdoc = KDoc.of("Hello World")
    assertThat(kdoc).hasToString("Hello World")
  }
}
