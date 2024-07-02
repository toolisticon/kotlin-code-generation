package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotationClass
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import org.junit.jupiter.api.Test

internal class KotlinAnnotationClassTest {
  @Test
  fun `build custom annotation class`() {
    val annotationClass: KotlinAnnotationClassSpec = buildAnnotationClass(className = ClassName("foo","CustomAnnotation")) {
      mustBeDocumented()
      addConstructorProperty("value", String::class)
    }

    println(annotationClass.get())
  }
}
