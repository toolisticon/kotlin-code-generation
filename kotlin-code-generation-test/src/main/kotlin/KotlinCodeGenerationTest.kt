package io.toolisticon.kotlin.generation.test

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
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
@OptIn(ExperimentalCompilerApi::class)
object KotlinCodeGenerationTest {

  @JvmStatic
  fun assertThat(actual: KotlinCompilationResult): KotlinCompilationAssert = KotlinCompilationAssert(actual)


  fun compile(cmd: KotlinCompilationCommand): KotlinCompilationResult {

    val result: JvmCompilationResult = KotlinCompilation().apply {
      sources = listOf(cmd.sourceFile)
      inheritClassPath = true

      this.
        // needed so kotlin compile does not spam the console log (would default to System.out)
      messageOutputStream = ByteArrayOutputStream()
      //compilerPluginRegistrars = listOf(SerializationComponentRegistrar())
    }.compile()

    return KotlinCompilationResult(cmd = cmd, result = result)
  }


}
