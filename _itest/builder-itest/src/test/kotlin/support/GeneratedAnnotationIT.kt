@file:OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)

package io.toolisticon.kotlin.generation.itest.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spec.toFileSpec
import io.toolisticon.kotlin.generation.support.GeneratedAnnotation
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationResult
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass

internal class GeneratedAnnotationIT {

  @Test
  fun `compile class with generated annotation`() {
    val file = buildDataClass("io.acme", "Foo") {
      addConstructorProperty("x", String::class)
      addAnnotation(GeneratedAnnotation())
    }.toFileSpec()

    println(file.code)

    val result: KotlinCompilationResult = KotlinCodeGenerationTest.compile(cmd = KotlinCompilationCommand(file))
    KotlinCodeGenerationTest.assertThat(result).errorMessages().isEmpty();
  }
}
