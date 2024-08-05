package io.toolisticon.kotlin.generation.itest.spi

import com.tschuchort.compiletesting.KotlinCompilation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SpiITest {

  @Test
  fun `use spi defined strategies and processors to generate code`() {
    val registry = KotlinCodeGenerationSpiRegistry.load<TestContext>()
    val context = TestContext(registry)

    val input = MapInput(
      className = className(packageName = "foo.bar", simpleName = "ExampleDataClass"),
      fields = mapOf(
        "name" to String::class
      )
    )

    val strategy = context.registry.getStrategy<MapInput, KotlinDataClassSpec>()

    val spec = strategy(context = context, input = input)
    val file = KotlinCodeGeneration.builder.fileBuilder(input.className).addType(spec).build()

    val result = KotlinCodeGenerationTest.compile(KotlinCompilationCommand(file))
    KotlinCodeGenerationTest.assertThat(result).hasExitCode(KotlinCompilation.ExitCode.OK)

    val foo = result.loadClass(input.className).java.getDeclaredConstructor(String::class.java).newInstance("Foo")

    assertThat(foo).hasToString("ExampleDataClass(name=Foo)")
  }
}
