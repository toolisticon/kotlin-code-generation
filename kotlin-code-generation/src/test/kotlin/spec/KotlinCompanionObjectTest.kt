
package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildCompanionObject
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinCompanionObjectTest {

  @Test
  fun build() {
    val r = buildClass("foo", "Dummy") {
      addType(buildCompanionObject() {
        addProperty("name", String::class) {
          addModifiers(KModifier.CONST)
          initializer(FORMAT_STRING, "hello world")
        }
      })
    }

    assertThat(r.code).isEqualToIgnoringWhitespace(
      """
      public class Dummy {
        public companion object {
           public const val name: kotlin.String = "hello world"
        }
      }
    """.trimIndent()
    )
  }
}
