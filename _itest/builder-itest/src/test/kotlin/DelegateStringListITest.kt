package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.itest.KotlinCodeGenerationITestConfig.ROOT_PACKAGE
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat as compileAssertThat

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class DelegateStringListITest {

  @Test
  fun `create and use string list`() {
    val list = KotlinCodeGeneration.buildDelegateListValueClass(ROOT_PACKAGE, "StringList", String::class) {
      propertyName("list")
    }.toFileSpec()

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(list))


    compileAssertThat(result).errorMessages().isEmpty()
    compileAssertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass: KClass<out Any> = result.loadClass(list.className)

    val values = listOf("a", "b", "c")

    val instance: List<String> = klass.primaryConstructor!!.call(values) as List<String>

    assertThat(instance).hasToString("StringList(list=[a, b, c])")
  }
}
