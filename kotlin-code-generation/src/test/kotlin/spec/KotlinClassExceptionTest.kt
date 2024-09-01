package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_NAME
import io.toolisticon.kotlin.generation.builder.KotlinFunSpecBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
class KotlinClassExceptionTest {

  @Test
  fun `create exception class`() {
    val e = buildClass(className("my","DummyException")) {
      superclass(RuntimeException::class)
      primaryConstructor(KotlinFunSpecBuilder.constructorBuilder().apply {
        addParameter("message", String::class)
      }

      )
      addSuperclassConstructorParameter(FORMAT_NAME, "message")
    }

    assertThat(e.code).isEqualToIgnoringWhitespace("""
      public class DummyException(
        message: kotlin.String,
      ) : java.lang.RuntimeException(message)
    """.trimIndent())
  }
}
