package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import org.junit.jupiter.api.Test

internal class KotlinFileSpecTest {

  @Target(AnnotationTarget.FILE)
  annotation class Foo

  @Test
  fun `build filespec`() {
    val builder: KotlinFileSpecBuilder = KotlinFileSpecBuilder.builder(ClassName("foo", "Bar")).invoke {
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
