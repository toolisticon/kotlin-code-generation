package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.dataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.full.primaryConstructor

@OptIn(ExperimentalCompilerApi::class)
internal class KotlinDataClassSpecTest {

  @Test
  fun `create simple data class`() {
    val className = ClassName("foo.bar", "Bar")
    val builder: KotlinDataClassSpecBuilder = dataClassBuilder(className)
      .addConstructorProperty(constructorPropertyBuilder("name", String::class))
      .addConstructorProperty(constructorPropertyBuilder("age", Int::class))

    val file = builder.build().toFileSpec()
    println(file.code)

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(file))

    assertThat(result).errorMessages().isEmpty()
    assertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass = result.loadClass()
    assertThat(klass.primaryConstructor!!.call("hello world", 25))
      .hasToString("Bar(name=hello world, age=25)")
  }
}
