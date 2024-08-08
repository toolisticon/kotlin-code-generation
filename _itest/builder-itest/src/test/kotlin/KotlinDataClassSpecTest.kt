package io.toolisticon.kotlin.generation.itest

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.dataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import org.junit.jupiter.api.Test

internal class KotlinDataClassSpecTest {

  @Test
  fun `create simple data class`() {
    val className = ClassName("foo.bar", "Bar")
    val builder: KotlinDataClassSpecBuilder = dataClassBuilder(className)
      .addConstructorProperty("name", String::class)
      .addConstructorProperty("age", Int::class)

    val spec = buildDataClass(className) {
      addConstructorProperty("name", String::class.asTypeName())
      addConstructorProperty("age", Int::class.asTypeName())
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
