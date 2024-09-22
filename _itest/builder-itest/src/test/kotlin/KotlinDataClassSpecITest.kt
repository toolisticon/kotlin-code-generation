package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.asTypeName
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.compile
import io.toolisticon.kotlin.generation.test.callPrimaryConstructor
import io.toolisticon.kotlin.generation.test.model.requireOk
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat as compileAssertThat

@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class KotlinDataClassSpecITest {

  @Test
  fun `create simple data class`() {
    val className = ClassName("foo.bar", "Bar")

    val spec = buildDataClass(className) {
      addConstructorProperty("name", String::class.asTypeName())
      addConstructorProperty("age", Int::class.asTypeName())
    }

    val file = spec.toFileSpec()

    val result = compile(file).requireOk()

    compileAssertThat(result).errorMessages().isEmpty()
    compileAssertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass = result.loadClass(className)
    assertThat(klass.callPrimaryConstructor<Any>("hello world", 25))
      .hasToString("Bar(name=hello world, age=25)")
  }
}
