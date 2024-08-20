package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotationClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
internal class KotlinAnnotationClassTest {
  @Test
  fun `build custom annotation class`() {
    val className = ClassName("foo", "CustomAnnotation")

    val annotationClass: KotlinAnnotationClassSpec = buildAnnotationClass(className) {
      mustBeDocumented()
      retention(AnnotationRetention.SOURCE)
      target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
      target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
      repeatable()
      addConstructorProperty("value", String::class)
    }

    assertThat(annotationClass.code).isEqualTo("""
      @kotlin.`annotation`.Target(allowedTargets = [kotlin.`annotation`.AnnotationTarget.ANNOTATION_CLASS, kotlin.`annotation`.AnnotationTarget.CLASS, kotlin.`annotation`.AnnotationTarget.FUNCTION])
      @kotlin.`annotation`.Retention(value = kotlin.`annotation`.AnnotationRetention.SOURCE)
      @kotlin.`annotation`.Repeatable
      @kotlin.`annotation`.MustBeDocumented
      public annotation class CustomAnnotation(
        public val `value`: kotlin.String,
      )

    """.trimIndent()
    )
  }
}
