package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.asTypeName
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
internal class DelegateStringLongMapITest {

  @Test
  fun `create and use string long map`() {
    val map = KotlinCodeGeneration.buildDelegateMapValueClass(
      packageName = ROOT_PACKAGE,
      simpleName = "StringLongMap",
      valueType = Long::class.asTypeName()
    ).toFileSpec()

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(map))
    compileAssertThat(result).errorMessages().isEmpty()
    compileAssertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass: KClass<out Any> = result.loadClass(map.className)

    val values = mapOf("a" to 1, "b" to 2, "c" to 3)

    val instance: Map<String, Long> = klass.primaryConstructor!!.call(values) as Map<String, Long>

    assertThat(instance).hasToString("StringLongMap(delegate={a=1, b=2, c=3})")
  }
}
