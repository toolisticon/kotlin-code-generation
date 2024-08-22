@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import jakarta.annotation.Generated
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class TypeSpecBuilderTest {

  @Test
  fun `verify builder`() {
    val builder: TypeSpecBuilder = TypeSpecBuilder(TypeSpec.classBuilder(ClassName("foo", "Bar")))

    builder.addAnnotation(Generated::class)

    assertThat(builder.build()).hasToString(
      """
      @jakarta.`annotation`.Generated
      public class Bar

    """.trimIndent()
    )
  }
}
