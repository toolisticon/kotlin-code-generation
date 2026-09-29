
package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinTypeAliasTest {

  @Test
  fun `buildTypeAlias without block using KClass`() {
    val spec = KotlinCodeGeneration.buildTypeAlias("UserId", String::class)

    assertThat(spec.code.trim()).isEqualTo("public typealias UserId = kotlin.String")
  }

  @Test
  fun `buildTypeAlias without block using TypeName`() {
    val spec = KotlinCodeGeneration.buildTypeAlias("UserId", String::class.asTypeName())

    assertThat(spec.code.trim()).isEqualTo("public typealias UserId = kotlin.String")
  }

  @Test
  fun `typeAliasBuilder with block`() {
    val spec = KotlinCodeGeneration.builder.typeAliasBuilder("UserId", String::class) {
      addModifiers(KModifier.INTERNAL)
      addKdoc("User identifier.")
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      /**
       * User identifier.
       */
      internal typealias UserId = kotlin.String
      """.trimIndent()
    )
  }
}
