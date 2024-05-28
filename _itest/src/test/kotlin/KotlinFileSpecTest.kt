package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import io.toolisticon.kotlin.generation.builder.KotlinFileBuilder
import org.junit.jupiter.api.Test

internal class KotlinFileSpecTest {

  @Target(AnnotationTarget.FILE)
  annotation class Foo

  @Test
  fun `build filespec`() {
    val builder: KotlinFileBuilder = KotlinFileBuilder.builder(ClassName("foo", "Bar")) {
      addFileComment("%L", "this is a comment.")
    }

    val b = builder {
      addAnnotation(Foo::class)
    }

    val spec = b.build()

    println(spec.code)
    println(spec)
  }
}
