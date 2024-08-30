package io.toolisticon.kotlin.generation.test.model

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.SourceFile
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.sourceFile
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

/**
 * Wraps all data needed for compilation.
 */
@ExperimentalKotlinPoetApi
@ExperimentalCompilerApi
data class KotlinCompilationCommand(
  val fileSpecs: List<KotlinFileSpec>
) {

  constructor(fileSpec: KotlinFileSpec) : this(listOf(fileSpec))

  operator fun plus(fileSpec: KotlinFileSpec) = copy(fileSpecs = fileSpecs + fileSpec)

  val sourceFiles: List<SourceFile> by lazy { fileSpecs.map { it.sourceFile() } }
}
