package io.toolisticon.kotlin.generation.test

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.spec.KotlinFileSpecList
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationResult
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import java.io.ByteArrayOutputStream

/**
 *
 * Original lib provides these configuration options. They will be included in this opinionated lib
 * when needed.
 *
 * ```kotlin
 *     val result = KotlinCompilation().apply {
 *         sources = listOf(kotlinSource, javaSource)
 *
 *         // pass your own instance of an annotation processor
 *         annotationProcessors = listOf(MyAnnotationProcessor())
 *
 *         // pass your own instance of a compiler plugin
 *         compilerPlugins = listOf(MyComponentRegistrar())
 * 	commandLineProcessors = listOf(MyCommandlineProcessor())
 *
 *         inheritClassPath = true
 *         messageOutputStream = System.out // see diagnostics in real time
 *     }.compile()
 *  ```
 */
@ExperimentalCompilerApi
@ExperimentalKotlinPoetApi
object KotlinCodeGenerationTest {

  /**
   * @see KotlinCompilationAssert
   */
  fun assertThat(actual: KotlinCompilationResult): KotlinCompilationAssert = KotlinCompilationAssert(actual)

  /**
   * Compiles files contained in command.
   */
  fun compile(cmd: KotlinCompilationCommand): KotlinCompilationResult {

    val result: JvmCompilationResult = KotlinCompilation().apply {
      sources = cmd.toList()
      inheritClassPath = true


      // needed so kotlin compile does not spam the console log (would default to System.out)
      messageOutputStream = ByteArrayOutputStream()
      //compilerPluginRegistrars = listOf(SerializationComponentRegistrar())
    }.compile()

    return KotlinCompilationResult(cmd = cmd, result = result)
  }


  /**
   * Convenience to compile files directly.
   * @see [KotlinCodeGenerationTest.compile]
   */
  fun compile(vararg fileSpec: KotlinFileSpec) = this.compile(
    KotlinCompilationCommand(
      KotlinFileSpecList.of(fileSpec.toList())
    )
  )

  /**
   * Extract sourceFile from spec.
   */
  fun KotlinFileSpec.sourceFile() = SourceFile.kotlin(name = this.fileName, contents = this.code)
}
