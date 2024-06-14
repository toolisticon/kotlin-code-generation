package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.dataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCompilerApi::class)
internal class KotlinDataClassSpecTest {

  @Test
  fun `create simple data class`() {
    val className = ClassName("foo.bar", "Bar")
    val builder: KotlinDataClassSpecBuilder = dataClassBuilder(className)
      .addConstructorProperty(constructorPropertyBuilder("name", String::class))
      .addConstructorProperty(constructorPropertyBuilder("age", Int::class))

    val spec = buildDataClass(className) {
      addConstructorProperty(KotlinConstructorPropertyBuilder.builder("name", String::class.asTypeName()))
      addConstructorProperty(KotlinConstructorPropertyBuilder.builder("age", Int::class.asTypeName()))
    }

//    val file = spec.toFileSpec()
//    println(file.code)

//    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(file))
//
//    assertThat(result).errorMessages().isEmpty()
//    assertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)
//
//    val klass = result.loadClass()
//    assertThat(klass.primaryConstructor!!.call("hello world", 25))
//      .hasToString("Bar(name=hello world, age=25)")
//  }
  }
}
