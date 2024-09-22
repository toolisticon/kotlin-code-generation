package io.toolisticon.kotlin.generation.test

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationResult
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi


/**
 * Assertj assertion for compilation result.
 */
@ExperimentalKotlinPoetApi
@ExperimentalCompilerApi
class KotlinCompilationAssert(
  actual: KotlinCompilationResult,
) : AbstractAssert<KotlinCompilationAssert, KotlinCompilationResult>(actual, KotlinCompilationAssert::class.java) {

  /**
   * Assertions on error messages.
   */
  fun errorMessages() = Assertions.assertThat(actual.errors)

  /**
   * Assertion on exitCode.
   */
  fun hasExitCode(exitCode: KotlinCompilation.ExitCode): KotlinCompilationAssert = apply {
    Assertions.assertThat(actual.exitCode).isEqualTo(exitCode)
  }
}
