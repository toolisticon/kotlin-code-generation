package io.toolisticon.kotlin.generation.test.model

import com.tschuchort.compiletesting.SourceFile
import io.toolisticon.kotlin.generation._BAK.KotlinFileSpec

data class KotlinCompilationCommand(
  val fileSpec: KotlinFileSpec
) {

  val sourceFile = SourceFile.kotlin(name = fileSpec.fileName, contents = fileSpec.code)
}
