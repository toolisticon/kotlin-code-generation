package io.toolisticon.kotlin.generation.test

import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationResult
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@ExperimentalCompilerApi
class KotlinCompilationAssert(
  actual: KotlinCompilationResult,
) : AbstractAssert<KotlinCompilationAssert, KotlinCompilationResult>(actual, KotlinCompilationAssert::class.java) {

  fun errorMessages()  = Assertions.assertThat(actual.errors)

  fun hasExitCode(exitCode: KotlinCompilation.ExitCode) : KotlinCompilationAssert = apply {
    Assertions.assertThat(actual.exitCode).isEqualTo(exitCode)
  }
}
