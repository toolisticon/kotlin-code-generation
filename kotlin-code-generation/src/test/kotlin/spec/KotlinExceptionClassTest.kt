package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
class KotlinExceptionClassTest {

  @Test
  fun `runtime exception with message`() {
    val exception = KotlinCodeGeneration.buildRuntimeExceptionClass("foo", "DummyException") {
      addKdoc(" This DummyException indicates something really went wrong. Totally!")
      messageTemplate("Something bad happened, expected=\$foo but got \$bar.")
      addParameter("foo", Int::class)
      addParameter("bar", Long::class)
      includeCause("e")
    }.toFileSpec()

    assertThat(exception.code).isEqualTo(
      """
      package foo

      import java.lang.RuntimeException
      import kotlin.Int
      import kotlin.Long
      import kotlin.Throwable

      /**
       *  This DummyException indicates something really went wrong. Totally!
       */
      public class DummyException(
        foo: Int,
        bar: Long,
        e: Throwable? = null,
      ) : RuntimeException(""${'"'}Something bad happened, expected=${'$'}foo but got ${'$'}bar.""${'"'}, e)

    """.trimIndent()
    )
  }
}
