package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.builder.KotlinDataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinParameterBuilder
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.full.primaryConstructor

@OptIn(ExperimentalCompilerApi::class)
internal class KotlinDataClassSpecTest {

  @Test
  fun `create simple data class`() {
    val className: ClassName = ClassName("foo.bar", "Bar")
    val builder: KotlinDataClassBuilder = KotlinDataClassBuilder.builder(className)
      .parameter(KotlinParameterBuilder.builder("name", String::class))

    val file = builder.build().toFileSpec()
    println(file.code)

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(file))

    assertThat(result).errorMessages().isEmpty()
    assertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass = result.loadClass()
    Assertions.assertThat(klass.primaryConstructor!!.call("hello world")).hasToString("Bar(name=hello world)")
  }
}
