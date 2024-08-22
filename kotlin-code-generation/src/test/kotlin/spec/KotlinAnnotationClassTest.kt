@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotationClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class KotlinAnnotationClassTest {

  @Test
  fun `build custom annotation class`() {
    val className = ClassName("foo", "CustomAnnotation")

    val annotationClass: KotlinAnnotationClassSpec = buildAnnotationClass(className) {
      mustBeDocumented()
      //retention(AnnotationRetention.SOURCE) //TODO: issue 27
      target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
      target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
      repeatable()
      addConstructorProperty("value", String::class)
    }

    assertThat(annotationClass.toFileSpec().code).isEqualToIgnoringWhitespace(
      """
      package foo

      import kotlin.String
      import kotlin.`annotation`.AnnotationTarget.ANNOTATION_CLASS
      import kotlin.`annotation`.AnnotationTarget.CLASS
      import kotlin.`annotation`.AnnotationTarget.FUNCTION
      import kotlin.`annotation`.MustBeDocumented
      import kotlin.`annotation`.Repeatable
      import kotlin.`annotation`.Target

      @Target(allowedTargets = [ANNOTATION_CLASS, CLASS, FUNCTION])
      @Repeatable
      @MustBeDocumented
      public annotation class CustomAnnotation(
        public val `value`: String,
      )
    """.trimIndent()
    )
  }
}
