package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.poet.FormatSpecifier
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
internal class CodeBlockArrayTest {

  @Test
  fun `string array`() {
    var array = CodeBlockArray<String>(FormatSpecifier.STRING)
    assertThat(array.build()).hasToString("[]")
    array += "foo"
    array += "bar"
    assertThat(array.build()).hasToString("""["foo", "bar"]""")
  }
}
