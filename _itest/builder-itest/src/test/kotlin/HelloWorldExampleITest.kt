
package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFile
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFun
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class HelloWorldExampleITest {

  @Test
  fun `hello world from kotlin poet talk`() {
    val name = ClassName("foo.bar", "HelloWorld")

    val type = buildClass(name) {
      addFunction(
        buildFun("helloWorld") {
          builder {
            returns(String::class)
            addCode("return %S", "Hello World!")
          }
        })
      addKdoc("%L", "Simple hello world class")
    }

    val file = buildFile(name) {
      addType(type)
    }
    println(file.code)

    assertThat(file.packageName).isEqualTo("foo.bar")

    val result = KotlinCodeGenerationTest.compile(cmd = KotlinCompilationCommand(fileSpec = file))

    println(result)

    KotlinCodeGenerationTest.assertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)
  }
}
