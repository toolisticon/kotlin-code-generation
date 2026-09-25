
package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinFunTest {

  @Test
  fun `addParameter without block using KClass`() {
    val fn = KotlinCodeGeneration.builder.funBuilder("greet") {
      addParameter("name", String::class)
    }.build()

    assertThat(fn.code.trim()).isEqualTo(
      """
      public fun greet(name: kotlin.String) {
      }
      """.trimIndent()
    )
  }

  @Test
  fun `addParameter without block using TypeName`() {
    val fn = KotlinCodeGeneration.builder.funBuilder("greet") {
      addParameter("name", String::class.asTypeName())
    }.build()

    assertThat(fn.code.trim()).isEqualTo(
      """
      public fun greet(name: kotlin.String) {
      }
      """.trimIndent()
    )
  }

  @Test
  fun `addParameter with configuration block`() {
    val fn = KotlinCodeGeneration.builder.funBuilder("fetch") {
      addParameter("timeout", Long::class) {
        defaultValue("%L", 5000L)
      }
      addParameter("retries", Int::class.asTypeName()) {
        defaultValue("%L", 3)
      }
    }.build()

    assertThat(fn.code.trim()).isEqualTo(
      """
      public fun fetch(timeout: kotlin.Long = 5_000, retries: kotlin.Int = 3) {
      }
      """.trimIndent()
    )
  }
}
