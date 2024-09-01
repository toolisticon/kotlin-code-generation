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

  /**
   * List all generated files
   */
  val generatedSources: List<String> by lazy {
    result.messages.lines()
      .filter { line -> line.endsWith(".kt") }
      .distinct().sorted()
      .map { "file://$it" }
  }

  fun loadClass(className: ClassName): KClass<out Any> = result.classLoader.loadClass(className.canonicalName).kotlin

  fun shouldBeOk() {
    assertThat(exitCode)
      .`as` { "compilation failed with errors: $errors" }
      .isEqualTo(KotlinCompilation.ExitCode.OK)
  }

  override fun toString() = toString(false)
  fun toString(includeCommand: Boolean) = "${this::class.simpleName}(" +
    if (includeCommand) {
      "cmd=$cmd, "
    } else {
      ""
    } +
    "exitCode=$exitCode, errors=$errors, generatedSources=$generatedSources)"
}
