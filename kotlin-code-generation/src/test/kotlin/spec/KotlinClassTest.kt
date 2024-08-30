
package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.classBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test


@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinClassTest {

  @Test
  fun `create class with default constructor and single hello world function`() {
    val builder = classBuilder("foo", "Bar")

    builder.addFunction("helloWorld") {
      returns(String::class)
      addCode("return $FORMAT_STRING", "Hello World!")
    }

    val spec = builder.spec()

    assertThat(spec.code).isEqualToIgnoringWhitespace(
      """public class Bar {
           public fun helloWorld(): kotlin.String = "Hello World!"
        }"""
    )
  }

  @Test
  fun `fail with constructorProperty AND primaryConstructor`() {
    val builder = classBuilder("foo", "Bar").apply {
      addConstructorProperty("foo", String::class)
      primaryConstructor(KotlinCodeGeneration.builder.constructorBuilder())
    }

    assertThatThrownBy { builder.build() }
      .isInstanceOf(IllegalStateException::class.java)
      .hasMessage("Decide if you want to use the constructorProperty support OR define a custom primary constructor, not both.")
  }


  @Test
  fun `provide primaryConstructor`() {
    val spec = classBuilder("foo", "Bar")
      .primaryConstructor(KotlinCodeGeneration.builder.constructorBuilder().addParameter("foo", Long::class))
      .build()

    assertThat(spec.code).isEqualToIgnoringWhitespace("""public class Bar(foo: kotlin.Long,)""".trimIndent())
  }

  @Test
  fun `provide constructorProperty`() {
    val spec = classBuilder("foo", "Bar")
      .addConstructorProperty("foo", Long::class)
      .build()

    assertThat(spec.code).isEqualToIgnoringWhitespace("""public class Bar(public val foo: kotlin.Long,)""".trimIndent())
  }
}
