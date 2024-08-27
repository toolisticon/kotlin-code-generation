@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnonymousClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFun
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinAnonymousClassTest {
  @Test
  fun build() {

    val r = buildAnonymousClass {
      addSuperinterface(Runnable::class)
      addFunction(buildFun("run"))
    }

    assertThat(r.code).isEqualToIgnoringWhitespace(
      """
      object : java.lang.Runnable {
        public fun run() {
        }
      }
    """.trimIndent()
    )
  }
}
