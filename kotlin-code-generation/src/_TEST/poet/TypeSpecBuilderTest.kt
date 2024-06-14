package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation._BAK.poet.TypeSpecBuilder
import jakarta.annotation.Generated
import org.junit.jupiter.api.Test

internal class TypeSpecBuilderTest {

  @OptIn(ExperimentalKotlinPoetApi::class)
  @Test
  fun `verify builder`() {
    val builder: TypeSpecBuilder = TypeSpecBuilder(TypeSpec.classBuilder(ClassName("foo","Bar")))

    var x: TypeSpecBuilder = builder.addAnnotation(Generated::class)

    println(builder.build().toString())
  }
}
