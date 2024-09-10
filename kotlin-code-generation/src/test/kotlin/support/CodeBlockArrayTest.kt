
package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.poet.FormatSpecifier
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.enumArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.kclassArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.numberArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.stringArray
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class CodeBlockArrayTest {

  @Test
  fun `string array`() {
    var array = CodeBlockArray<String>(FormatSpecifier.STRING)
    assertThat(array.build()).hasToString("[]")
    array += "foo"
    array += "bar"
    assertThat(array.build()).hasToString("""["foo", "bar"]""")
  }

  @Test
  fun `empty array`() {
    assertThat(stringArray().build()).hasToString("[]")
  }

  @Test
  fun `stringArray with two items`() {
    assertThat(stringArray("a", "b").build()).hasToString("""["a", "b"]""")
  }

  enum class ABC {
    A, B
  }

  @Test
  fun `enumArray with two items`() {
    assertThat(
      enumArray(
        ABC.A,
        ABC.B
      ).build()
    ).hasToString("""[io.toolisticon.kotlin.generation.support.CodeBlockArrayTest.ABC.A, io.toolisticon.kotlin.generation.support.CodeBlockArrayTest.ABC.B]""")
  }

  @Test
  fun `numberArray with two items`() {
    assertThat(numberArray(2, 3).build()).hasToString("""[2, 3]""")
  }

  @Test
  fun `kclassArray with two items`() {
    assertThat(kclassArray(String::class, Long::class).build()).hasToString("""[kotlin.String::class, kotlin.Long::class]""")
  }
}
