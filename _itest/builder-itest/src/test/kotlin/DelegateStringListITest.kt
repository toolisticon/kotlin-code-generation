package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.itest.KotlinCodeGenerationITestConfig.ROOT_PACKAGE
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.compile
import io.toolisticon.kotlin.generation.test.callPrimaryConstructor
import io.toolisticon.kotlin.generation.test.model.requireOk
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat as compileAssertThat

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class DelegateStringListITest {

  @Test
  fun `create and use string list`() {
    val listSpec = KotlinCodeGeneration.buildDelegateListValueClass(ROOT_PACKAGE, "StringList", String::class) {
      propertyName("list")
    }.toFileSpec()

    val result = compile(listSpec).requireOk()

    compileAssertThat(result).errorMessages().isEmpty()
    compileAssertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass: KClass<out Any> = result.loadClass(listSpec.className)

    val values = listOf("a", "b", "c")

    val instance: List<String> = klass.callPrimaryConstructor(values)

    assertThat(instance).hasToString("StringList(list=[a, b, c])")
  }
}
