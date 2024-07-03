package io.toolisticon.kotlin.generation.test.model

import com.tschuchort.compiletesting.SourceFile
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.sourceFile

data class KotlinCompilationCommand(
  val fileSpecs: List<KotlinFileSpec>
) {

  constructor(fileSpec: KotlinFileSpec) : this(listOf(fileSpec))

  operator fun plus(fileSpec: KotlinFileSpec) = copy(fileSpecs = fileSpecs + fileSpec)


  val sourceFiles: List<SourceFile> by lazy { fileSpecs.map { it.sourceFile() } }
}
