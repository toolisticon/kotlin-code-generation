package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class KotlinFileSpecITest {

  @Target(AnnotationTarget.FILE)
  annotation class Foo

  @Test
  fun name() {
    val spec = FileSpec.builder(ClassName("some", "Stuff"))
      .addFileComment("%L", "this is a comment")
      .addAnnotation(Foo::class)
      .build()

    println(spec)
  }

  @Test
  fun `build filespec`() {
    val builder: KotlinFileSpecBuilder = KotlinFileSpecBuilder.builder(ClassName("foo", "Bar")).builder {
      addFileComment("%L", "this is a comment.")
    }

    val b = builder.builder {
      addAnnotation(Foo::class)
    }

    val spec = b.build()

    println(spec.code)
  }
}
