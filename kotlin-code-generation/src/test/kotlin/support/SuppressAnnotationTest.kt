
package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.classBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.fileBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class SuppressAnnotationTest {
  private val className = ClassName("foo", "Bar")
  private val typeBuilder = classBuilder(className)
  private val fileBuilder = fileBuilder(className)

  @Test
  fun `single unused on class`() {
    val suppress = SuppressAnnotation(SUPPRESS_UNUSED).spec()

    val type = typeBuilder.addAnnotation(suppress).build()

    assertThat(type.code).isEqualTo(
      """
      @kotlin.Suppress("unused")
      public class Bar

    """.trimIndent()
    )
  }

  @Test
  fun `multiple on class`() {
    val suppress = SuppressAnnotation(SUPPRESS_UNUSED)
      .plus(SUPPRESS_REDUNDANT_VISIBILITY_MODIFIER)
      .spec()

    val type = typeBuilder.addAnnotation(suppress).build()

    assertThat(type.code).isEqualTo(
      """
      @kotlin.Suppress("RedundantVisibilityModifier", "unused")
      public class Bar

    """.trimIndent()
    )
  }

  @Test
  fun `multiple on file`() {
    val suppress = SuppressAnnotation(SUPPRESS_UNUSED)
      .plus(SUPPRESS_REDUNDANT_VISIBILITY_MODIFIER)
      .spec()

    val file = fileBuilder.addAnnotation(suppress).addType(typeBuilder.spec()).build()

    assertThat(file.code).isEqualTo(
      """
      @file:Suppress("RedundantVisibilityModifier", "unused")

      package foo

      import kotlin.Suppress

      public class Bar

""".trimIndent()
    )
  }
}
