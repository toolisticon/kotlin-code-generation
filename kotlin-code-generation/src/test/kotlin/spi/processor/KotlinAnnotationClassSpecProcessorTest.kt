
package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation._test.MutableSpiRegistry
import io.toolisticon.kotlin.generation._test.TestContext
import io.toolisticon.kotlin.generation._test.TestInput
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationClassSpecBuilder
import io.toolisticon.kotlin.generation.spec.toFileSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinAnnotationClassSpecProcessorTest {

  class AddRetentionProcessor : KotlinAnnotationClassSpecProcessor<TestContext, TestInput>(
    contextType = TestContext::class, inputType = TestInput::class
  ) {
    override fun invoke(context: TestContext, input: TestInput, builder: KotlinAnnotationClassSpecBuilder): KotlinAnnotationClassSpecBuilder {
      return builder.retention(AnnotationRetention.BINARY)
    }

    override fun test(context: TestContext, input: Any): Boolean = true
  }

  @Test
  fun `process builder`() {
    val context = TestContext(className("foo", "Dummy"), MutableSpiRegistry())
    assertThat(context.processors(AddRetentionProcessor::class)).isEmpty()
    context.registry.processorList.add(AddRetentionProcessor())
    assertThat(context.processors(AddRetentionProcessor::class)).isNotEmpty

    val builder = KotlinAnnotationClassSpecBuilder.builder(context.rootClassName)

    context.processors(AddRetentionProcessor::class).executeSingle(context, TestInput(), builder)

    assertThat(builder.spec().toFileSpec().code).contains("@Retention")
  }
}
