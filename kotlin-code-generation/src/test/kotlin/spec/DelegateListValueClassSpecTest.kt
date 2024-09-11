package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDelegateListValueClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
internal class DelegateListValueClassSpecTest {

  @Test
  fun `create delegate list`() {

    val list = buildDelegateListValueClass(
      packageName = "xyz",
      simpleName = "MyList",
      items = String::class
    ).toFileSpec()

    assertThat(list.code).isEqualTo("""
      package xyz

      import kotlin.String
      import kotlin.collections.List
      import kotlin.jvm.JvmInline

      @JvmInline
      public value class MyList(
        private val `delegate`: List<String>,
      ) : List<String> by delegate

    """.trimIndent())
  }
}
