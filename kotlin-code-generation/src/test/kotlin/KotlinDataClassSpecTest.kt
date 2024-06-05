package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinDataClassSpecTest {

  @Test
  fun `create foo`() {
    val className = ClassName("test","Foo")

    val spec = buildDataClass(className) {
//      addConstructorProperty(constructorPropertyBuilder("x", Int::class))
//      addConstructorProperty(constructorPropertyBuilder("y", Long::class))
    }

    assertThat(spec.code.trim()).isEqualTo("""
      public data class Foo(
        public val x: kotlin.Int,
        public val y: kotlin.Long,
      )
    """.trimIndent())
  }
}
