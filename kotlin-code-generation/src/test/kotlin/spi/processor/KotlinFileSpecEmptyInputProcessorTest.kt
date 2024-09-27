package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.fileBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation._test.MutableSpiRegistry
import io.toolisticon.kotlin.generation._test.TestContext
import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinFileSpecEmptyInputProcessorTest {

  @Test
  fun `add doc to file via processor`() {
    val file = fileBuilder("foo", "bar")

    class AddDocProcessor : KotlinFileSpecEmptyInputProcessor<TestContext>(TestContext::class) {
      override fun invoke(context: TestContext, builder: KotlinFileSpecBuilder): KotlinFileSpecBuilder {
        return builder.addFileComment(KotlinCodeGeneration.format.FORMAT_STRING, "Hello World")
      }
    }

    AddDocProcessor().execute(TestContext(className("foo", "bar"), MutableSpiRegistry()), file)

    assertThat(file.spec().code).isEqualToIgnoringWhitespace("""
      // "Hello World"
      package foo
    """.trimIndent())
  }
}
