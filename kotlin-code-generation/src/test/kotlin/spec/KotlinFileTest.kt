
package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinFileTest {

  @Test
  fun `addTypeAlias to file without block`() {
    val file = KotlinCodeGeneration.builder.fileBuilder("com.example", "MyFile") {
      addTypeAlias("UserId", String::class)
      addTypeAlias("ItemId", Long::class.asTypeName())
    }.build()

    assertThat(file.code.trim()).isEqualTo(
      """
      package com.example

      import kotlin.Long
      import kotlin.String

      public typealias UserId = String

      public typealias ItemId = Long
      """.trimIndent()
    )
  }

  @Test
  fun `addTypeAlias to file with block`() {
    val file = KotlinCodeGeneration.builder.fileBuilder("com.example", "MyFile") {
      addTypeAlias("InternalId", String::class) {
        addModifiers(KModifier.INTERNAL)
        addKdoc("Internal identifier.")
      }
    }.build()

    assertThat(file.code.trim()).isEqualTo(
      """
      package com.example

      import kotlin.String

      /**
       * Internal identifier.
       */
      internal typealias InternalId = String
      """.trimIndent()
    )
  }
}
