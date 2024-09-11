package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildRuntimeExceptionClass
import io.toolisticon.kotlin.generation.itest.KotlinCodeGenerationITestConfig.ROOT_PACKAGE
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat as compileAssertThat

@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class DummyExceptionITest {

  @Test
  fun `generate and create dummy exception`() {
    val exceptionFile = buildRuntimeExceptionClass(ROOT_PACKAGE, "DummyException") {
      messageTemplate("Dummy exception: expected: \$expected, actual: '\$actual'.")
      addConstructorProperty("expected", Boolean::class)
      addParameter("actual", String::class)
      includeCause()
    }.toFileSpec()


    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(exceptionFile))

    compileAssertThat(result).errorMessages().isEmpty()
    compileAssertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val c: KClass<out Any> = result.loadClass(exceptionFile.className)

    val cause = IllegalStateException("foo")
    val e: RuntimeException = c.primaryConstructor!!.call(true, "false", cause) as RuntimeException

    assertThat(e.localizedMessage).isEqualTo("Dummy exception: expected: true, actual: 'false'.")

    // TODO try to get value via pure kotlin without falling back to java
    val expectedProperty: KProperty1<out Any, *> = c.memberProperties.single { it.name == "expected" }
    val field = c.java.getDeclaredField("expected").apply { isAccessible = true }

    val expectedValue = field.get(e) as Boolean

    assertThat(expectedValue).isTrue()
  }
}
