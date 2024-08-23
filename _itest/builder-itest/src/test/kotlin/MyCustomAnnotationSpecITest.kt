@file:OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)

package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.asClassName
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFile
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.assertThat as assertThatCompilation

// @Disabled("see issue #27")
internal class MyCustomAnnotationSpecITest {

  @Test
  fun `generate class with custom annotation`() {
    val name = ClassName(KotlinCodeGenerationITestConfig.ROOT_PACKAGE, "MyClass")

    val customAnnotation = buildAnnotation(MyCustomAnnotationSpec.name) {
      addStringMember("value", "hello")
    }

    val myClass = KotlinCodeGeneration.buildClass(name) {
      addAnnotation(customAnnotation)
    }

    val file = buildFile(name) {
      addType(myClass)
    }

    println(myClass.code)

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(MyCustomAnnotationSpec.file).plus(file))

    assertThatCompilation(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val klass: KClass<out Any> = result.loadClass(name)
    assertThat(klass::class.asClassName()).isEqualTo(name)
//    FIXME assertThat(klass.annotations).hasSize(1)
    val annotation: Annotation = klass.annotations[0]
    assertThat(annotation::class.asClassName()).isEqualTo(MyCustomAnnotationSpec.name)
  }
}
