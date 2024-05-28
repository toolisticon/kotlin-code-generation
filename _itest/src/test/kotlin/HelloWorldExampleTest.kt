@file:OptIn(ExperimentalCompilerApi::class)

package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.builder.KotlinFileBuilder
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

internal class HelloWorldExampleTest {

  @Test
  fun `hello world from kotlin poet talk`() {
    val name = ClassName("foo.bar", "HelloWorld")

    val type = TypeSpec.classBuilder(name)
      .addKdoc("%L", "Simple hello world class")
      .addFunction(
        FunSpec.builder("helloWorld")
          .returns(String::class)
          .addCode("return %S", "Hello World!")
          .build()
      )
      .build()

    val file = KotlinFileBuilder.builder(name).invoke {
      addType(type)
    }.build()

    println(file.code)

    Assertions.assertThat(file.packageName).isEqualTo("foo.bar")

    val result = KotlinCodeGenerationTest.compile(cmd = KotlinCompilationCommand(fileSpec = file))

    println(result)

    assertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)


  }
}
