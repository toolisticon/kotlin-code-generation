
package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinEnumClassTest {

  @Test
  fun `generate simple enum`() {
    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "SimpleEnum") {
      addEnumConstant("FOO")
      addEnumConstant("BAR")
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class SimpleEnum {
        FOO,
        BAR,
      }
      """.trimIndent()
    )
  }

  @Test
  fun `generate enum with constructor property using format and args`() {
    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "MyEnum") {
      addConstructorProperty("value", String::class)
      addEnumConstant("FOO", "%S", "foo")
      addEnumConstant("BAR", "%S", "bar")
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class MyEnum(
        public val `value`: kotlin.String,
      ) {
        FOO("foo"),
        BAR("bar"),
        ;
      }
      """.trimIndent()
    )
  }

  @Test
  fun `generate enum with constructor property using codeBlock`() {
    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "MyEnum") {
      addConstructorProperty("value", String::class)
      addEnumConstant("FOO", CodeBlock.of("%S", "foo"))
      addEnumConstant("BAR", CodeBlock.of("%S", "bar"))
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class MyEnum(
        public val `value`: kotlin.String,
      ) {
        FOO("foo"),
        BAR("bar"),
        ;
      }
      """.trimIndent()
    )
  }

  @Test
  fun `generate enum with constructor property using lambda receiver`() {
    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "MyEnum") {
      addConstructorProperty("value", String::class)
      addEnumConstant("FOO") {
        addSuperclassConstructorParameter("%S", "foo")
      }
      addEnumConstant("BAR") {
        addSuperclassConstructorParameter("%S", "bar")
      }
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class MyEnum(
        public val `value`: kotlin.String,
      ) {
        FOO("foo"),
        BAR("bar"),
        ;
      }
      """.trimIndent()
    )
  }

  @Test
  fun `generate enum with constructor property using anonymous class supplier`() {
    val fooSupplier = KotlinCodeGeneration.builder.anonymousClassBuilder {
      addSuperclassConstructorParameter("%S", "foo")
    }
    val barSpec = KotlinCodeGeneration.builder.anonymousClassBuilder {
      addSuperclassConstructorParameter("%S", "bar")
    }.build()

    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "MyEnum") {
      addConstructorProperty("value", String::class)
      addEnumConstant("FOO", fooSupplier)
      addEnumConstant("BAR", barSpec)
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class MyEnum(
        public val `value`: kotlin.String,
      ) {
        FOO("foo"),
        BAR("bar"),
        ;
      }
      """.trimIndent()
    )
  }

  @Test
  fun `generate enum with constructor property using TypeSpec`() {
    val fooTypeSpec = TypeSpec.anonymousClassBuilder()
      .addSuperclassConstructorParameter("%S", "foo")
      .build()

    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "MyEnum") {
      addConstructorProperty("value", String::class)
      addEnumConstant("FOO", fooTypeSpec)
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class MyEnum(
        public val `value`: kotlin.String,
      ) {
        FOO("foo"),
        ;
      }
      """.trimIndent()
    )
  }

  @Test
  fun `generate enum with multiple constructor properties`() {
    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "ComplexEnum") {
      addConstructorProperty("id", Int::class)
      addConstructorProperty("label", String::class)
      addEnumConstant("FIRST") {
        addSuperclassConstructorParameter("%L", 1)
        addSuperclassConstructorParameter("%S", "first")
      }
      addEnumConstant("SECOND", "%L, %S", 2, "second")
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class ComplexEnum(
        public val id: kotlin.Int,
        public val label: kotlin.String,
      ) {
        FIRST(1, "first"),
        SECOND(2, "second"),
        ;
      }
      """.trimIndent()
    )
  }

  @Test
  fun `generate enum with value class constructor property`() {
    val spec = KotlinCodeGeneration.builder.enumClassBuilder("foo", "MyEnum") {
      addConstructorProperty("value", MyJvmInlineClass::class)
      addEnumConstant("FOO", "%T(%L)", MyJvmInlineClass::class, 1)
      addEnumConstant("BAR", "%T(%L)", MyJvmInlineClass::class, 2)
    }.build()

    assertThat(spec.code.trim()).isEqualTo(
      """
      public enum class MyEnum(
        public val `value`: io.toolisticon.kotlin.generation.spec.KotlinEnumClassTest.MyJvmInlineClass,
      ) {
        FOO(io.toolisticon.kotlin.generation.spec.KotlinEnumClassTest.MyJvmInlineClass(1)),
        BAR(io.toolisticon.kotlin.generation.spec.KotlinEnumClassTest.MyJvmInlineClass(2)),
        ;
      }
      """.trimIndent()
    )
  }

  @JvmInline
  value class MyJvmInlineClass(val value: Int)
}
