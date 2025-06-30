package io.toolisticon.kotlin.generation.itest.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.spi.filter.hasContextType
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.spi.filter.hasNameIn
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationSpiList
import io.toolisticon.kotlin.generation.spi.strategy.executeSingle
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.compile
import io.toolisticon.kotlin.generation.test.callPrimaryConstructor
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import io.toolisticon.kotlin.generation.test.model.requireOk
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class SpiITest {

  @Test
  fun `init is empty`() {
    val list = KotlinCodeGenerationSpiList()
      .filter(hasContextType(TestContext::class))
      .filterNot(hasNameIn(setOf("io.toolisticon.kotlin.generation.itest.spi.TestDataClassStrategy")))

    assertThat(list.strategies).isEmpty()
    assertThat(list.processors).isEmpty()
  }

  @Test
  fun `use spi defined strategies and processors to generate code`() {
    val registry = KotlinCodeGenerationSpiList(
      TestDataClassStrategy()
    ).registry()
    val context = TestContext(registry)


    val input = MapInput(
      className = className(packageName = "foo.bar", simpleName = "ExampleDataClass"),
      fields = mapOf(
        "name" to String::class,
        "foo" to Long::class
      )
    )

    val spec = requireNotNull(context.registry.strategies.filter(TestDataClassStrategy::class).executeSingle(context, input))
    val file = KotlinCodeGeneration.builder.fileBuilder(input.className).addType(spec).build()

    val result = compile(KotlinCompilationCommand(file)).requireOk()

    val foo: Any = result.loadClass(input.className).callPrimaryConstructor("Foo", 5L)

    assertThat(foo).hasToString("ExampleDataClass(name=Foo, foo=5)")
  }
}
