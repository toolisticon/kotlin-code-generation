package io.toolisticon.kotlin.generation.test.model

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat
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

  /**
   * OK or ERROR.
   */
  val exitCode = result.exitCode

  /**
   * List of errors, one per parsed error line.
   */
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

  override fun toString() = toString(false)
  fun toString(includeCommand: Boolean) = "${this::class.simpleName}(" +
    if (includeCommand) {
      "cmd=$cmd, "
    } else {
      ""
    } +
    "exitCode=$exitCode, errors=$errors, generatedSources=$generatedSources)"
}

/**
 * Convenience for `assertThat(result).hasExitCode().
 */
@ExperimentalCompilerApi
@ExperimentalKotlinPoetApi
fun KotlinCompilationResult.requireOk() = apply {
  assertThat(this)
    .`as` { "compilation failed with errors: ${errors}." }
    .hasExitCode(KotlinCompilation.ExitCode.OK)
}

