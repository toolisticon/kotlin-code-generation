package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asMemberName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalKotlinPoetApi
internal class KotlinCodeGenerationTest {

  @Nested
  inner class Name {

    @Test
    fun `join members to array`() {
      val m1 = AnnotationTarget.VALUE_PARAMETER.asMemberName()
      val m2 = AnnotationTarget.ANNOTATION_CLASS.asMemberName()
      val code = listOf(m1,m2).asCodeBlock()

      assertThat(code).hasToString("[kotlin.`annotation`.AnnotationTarget.VALUE_PARAMETER, kotlin.`annotation`.AnnotationTarget.ANNOTATION_CLASS]")
    }
  }

}
