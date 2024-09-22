package io.toolisticon.kotlin.generation.itest.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.spi.registry
import io.toolisticon.kotlin.generation.spi.strategy.executeSingle
import io.toolisticon.kotlin.generation.test.KotlinCodeGenerationTest.compile
import io.toolisticon.kotlin.generation.test.callPrimaryConstructor
import io.toolisticon.kotlin.generation.test.model.KotlinCompilationCommand
import io.toolisticon.kotlin.generation.test.model.requireOk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class, ExperimentalCompilerApi::class)
internal class SpiITest {

  @Test
  fun `init fails when only strategy is excluded`() {
    assertThatThrownBy {
      registry(
        contextTypeUpperBound = TestContext::class,
        exclusions = setOf("io.toolisticon.kotlin.generation.itest.spi.TestDataClassStrategy")
      )
    }.isInstanceOf(IllegalStateException::class.java)
      .hasMessage("No serviceInstances found, configure `META-INF/services/io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi`, and/or check your exclusions filter.")
  }

  @Test
  fun `use spi defined strategies and processors to generate code`() {
    val registry = registry(contextTypeUpperBound = TestContext::class)
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
