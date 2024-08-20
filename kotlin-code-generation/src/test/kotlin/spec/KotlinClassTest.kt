package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.classBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
internal class KotlinClassTest {

  @Test
  fun `create class with default constructor and single hello world function`() {
    val builder = classBuilder("foo", "Bar")

    builder.addFunction("helloWorld") {
      returns(String::class)
      addCode("return $FORMAT_STRING", "Hello World!")
    }

    val spec = builder.spec()

    assertThat(spec.get().toString().trim()).isEqualToIgnoringWhitespace(
      """public class Bar {
           public fun helloWorld(): kotlin.String = "Hello World!"
        }"""
    )
  }
}
