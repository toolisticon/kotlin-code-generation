package io.toolisticon.kotlin.generation.test.model

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import kotlin.reflect.KClass

/**
 * Wraps the compilation result and provides useful tooling.
 */
@ExperimentalCompilerApi
@ExperimentalKotlinPoetApi
data class KotlinCompilationResult(
  val cmd: KotlinCompilationCommand,
  val result: JvmCompilationResult
) {

  val exitCode = result.exitCode

  val errors: List<KotlinCompilationError> by lazy {
    result.messages.lines()
      .filter { it.startsWith("e:") }
      .map { it.removePrefix("e: ") }
      .map {
        val file = it.substringBefore(" ")
        val msg = it.substringAfter(" ")
        KotlinCompilationError(message = msg, file = file)
      }
  }

  fun loadClass(className: ClassName): KClass<out Any> = result.classLoader.loadClass(className.canonicalName).kotlin

  fun shouldBeOk() {
    assertThat(exitCode)
      .`as` { "compilation failed with errors: $errors" }
      .isEqualTo(KotlinCompilation.ExitCode.OK)
  }

  override fun toString() = "${this::class.simpleName}(cmd=$cmd, exitCode=$exitCode, errors=$errors)"
}
