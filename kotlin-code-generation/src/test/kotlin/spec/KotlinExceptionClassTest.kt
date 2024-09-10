package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_NAME
import io.toolisticon.kotlin.generation.builder.KotlinFunSpecBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
class KotlinExceptionClassTest {

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

  @Test
  fun `runtime exception without parameters`() {
    val exception = KotlinCodeGeneration.buildExceptionClass("foo", "DummyException", RuntimeException::class).toFileSpec()

    assertThat(exception.code).isEqualTo("""
      package foo

      import java.lang.RuntimeException

      public class DummyException : RuntimeException()

    """.trimIndent())
  }

  @Test
  fun `runtime exception with message`() {
    val exception = KotlinCodeGeneration.buildExceptionClass("foo", "DummyException", RuntimeException::class){
      addKdoc("""
        This DummyException indicates something really went wrong. Totally!

      """.trimIndent())
      messageTemplate("Something bad happened, expected=\$foo but got \$bar.")
      addParameter("foo", Int::class)
      addParameter("bar", Long::class)
      includeCause()
    }.toFileSpec()

    println(exception.code)

    assertThat(exception.code).isEqualTo("""
      package foo

      import java.lang.RuntimeException

      public class DummyException : RuntimeException()

    """.trimIndent())
  }
}
