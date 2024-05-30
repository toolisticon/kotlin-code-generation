package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.KotlinCodeGeneration.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.dataClassBuilder
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinDataClassSpecTest {

  @Test
  fun `create foo`() {
    val builder = dataClassBuilder("test", "Foo")
      .addConstructorProperty(constructorPropertyBuilder("x", Int::class))
      .addConstructorProperty(constructorPropertyBuilder("y", Long::class))

    val spec = builder.build()

    assertThat(spec.code.trim()).isEqualTo("""
      public data class Foo(
        public val x: kotlin.Int,
        public val y: kotlin.Long,
      )
    """.trimIndent())
  }
}
