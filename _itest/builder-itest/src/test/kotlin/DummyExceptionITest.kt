package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildRuntimeExceptionClass
import io.toolisticon.kotlin.generation.itest.KotlinCodeGenerationITestConfig.ROOT_PACKAGE
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.compile
import io.toolisticon.kotlin.generation.test.callPrimaryConstructor
import io.toolisticon.kotlin.generation.test.getFieldValue
import io.toolisticon.kotlin.generation.test.model.requireOk
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

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

    val result = compile(exceptionFile).requireOk()

    val c: KClass<out Any> = result.loadClass(exceptionFile.className)

    val cause = IllegalStateException("foo")
    val e: RuntimeException = c.callPrimaryConstructor(true, "false", cause)

    assertThat(e.localizedMessage).isEqualTo("Dummy exception: expected: true, actual: 'false'.")

    val expectedValue: Boolean = e.getFieldValue("expected")
    assertThat(expectedValue).isTrue()
  }
}
