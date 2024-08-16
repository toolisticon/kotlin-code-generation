package io.toolisticon.kotlin.generation.itest.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@ExperimentalCompilerApi
@ExperimentalKotlinPoetApi
internal class SpiITest {

  @Test
  fun `use spi defined strategies and processors to generate code`() {
    val registry = KotlinCodeGeneration.spi.repository(TestContext::class)
    val context = TestContext(registry)

    val input = MapInput(
      className = className(packageName = "foo.bar", simpleName = "ExampleDataClass"),
      fields = mapOf(
        "name" to String::class,
        "foo" to Long::class
      )
    )

    val strategy = context.findStrategies(MapInput::class, KotlinDataClassSpec::class).single()

    val spec = strategy(context = context, input = input)
    val file = KotlinCodeGeneration.builder.fileBuilder(input.className).addType(spec).build()

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(file))
    KotlinCodeGenerationTest.assertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val foo = result.loadClass(input.className).java.getDeclaredConstructor(String::class.java, Long::class.java).newInstance("Foo", 5L)

    assertThat(foo).hasToString("ExampleDataClass(name=Foo, foo=5)")
  }
}
