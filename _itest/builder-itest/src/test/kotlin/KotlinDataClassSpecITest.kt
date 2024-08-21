@file:OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class, ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.asTypeName
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.full.primaryConstructor
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat as compileAssertThat

internal class KotlinDataClassSpecITest {

  @Test
  fun `create simple data class`() {
    val className = ClassName("foo.bar", "Bar")

    val spec = buildDataClass(className) {
      addConstructorProperty("name", String::class.asTypeName())
      addConstructorProperty("age", Int::class.asTypeName())
    }

    val file = spec.toFileSpec()

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(file))

    compileAssertThat(result).errorMessages().isEmpty()
    compileAssertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass = result.loadClass(className)
    assertThat(klass.primaryConstructor!!.call("hello world", 25))
      .hasToString("Bar(name=hello world, age=25)")
  }

}
