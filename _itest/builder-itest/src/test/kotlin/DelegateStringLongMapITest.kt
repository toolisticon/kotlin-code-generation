package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.asTypeName
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
internal class DelegateStringLongMapITest {

  @Test
  fun `create and use string long map`() {
    val mapSpec = KotlinCodeGeneration.buildDelegateMapValueClass(
      packageName = ROOT_PACKAGE,
      simpleName = "StringLongMap",
      valueType = Long::class.asTypeName()
    ) {
      propertyName("map")
    }.toFileSpec()

    val result = compile(mapSpec).requireOk()
    compileAssertThat(result).errorMessages().isEmpty()
    compileAssertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass: KClass<out Any> = result.loadClass(mapSpec.className)

    val values = mapOf("a" to 1, "b" to 2, "c" to 3)

    val instance: Map<String, Long> = klass.callPrimaryConstructor(values)

    assertThat(instance).hasToString("StringLongMap(map={a=1, b=2, c=3})")
  }
}
