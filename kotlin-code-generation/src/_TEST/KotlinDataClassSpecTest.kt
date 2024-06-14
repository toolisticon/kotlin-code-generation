package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinDataClassSpecTest {

  @Test
  fun `create foo`() {
    val className = ClassName("test", "Foo")

    val spec = buildDataClass(className) {
      addConstructorProperty("x", Int::class.asTypeName())
      addConstructorProperty(buildConstructorProperty("y", Long::class.asTypeName()) {
        makePrivate()
      })
    }

    assertThat(spec.code.trim()).isEqualTo(
      """
      public data class Foo(
        public val x: kotlin.Int,
        private val y: kotlin.Long,
      )
    """.trimIndent()
    )
  }
}
