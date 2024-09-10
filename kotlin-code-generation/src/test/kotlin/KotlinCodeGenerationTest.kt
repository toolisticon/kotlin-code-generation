
package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.className
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.generateFiles
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asMemberName
import io.toolisticon.kotlin.generation._test.MutableSpiRegistry
import io.toolisticon.kotlin.generation._test.TestContext
import io.toolisticon.kotlin.generation._test.TestDeclaration
import io.toolisticon.kotlin.generation._test.TestDeclarationFileStrategy
import io.toolisticon.kotlin.generation._test.TestInput
import io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecStrategy
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
internal class KotlinCodeGenerationTest {

  @Nested
  inner class Name {

    @Test
    fun `join members to array`() {
      val m1 = AnnotationTarget.VALUE_PARAMETER.asMemberName()
      val m2 = AnnotationTarget.ANNOTATION_CLASS.asMemberName()
      val code = listOf(m1, m2).asCodeBlock()

      assertThat(code).hasToString("[kotlin.`annotation`.AnnotationTarget.VALUE_PARAMETER, kotlin.`annotation`.AnnotationTarget.ANNOTATION_CLASS]")
    }
  }

  @Nested
  inner class GenerateFilesTest {
    private val registry = MutableSpiRegistry()
    private val className = className("foo", "Bar")
    private val testInput = TestInput().put("name", String::class)
    private val testDeclaration = TestDeclaration(className, testInput)

    @Test
    fun `fail when no strategy registered`() {
      assertThat(registry.strategies).isEmpty()

      assertThatThrownBy {
        generateFiles<TestDeclaration, TestContext, KotlinFileSpecStrategy<TestContext, TestDeclaration>>(
          input = testDeclaration,
          contextFactory = { TestContext(it.rootClassName, registry) }
        )
      }.isInstanceOf(IllegalStateException::class.java)
        .hasMessage(
          "No applicable strategy found/filtered for " +
            "`class io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecStrategy`."
        )
    }

    @Test
    fun `fail when no strategy matches`() {
      registry.strategyList.add(TestDeclarationFileStrategy().apply {
        fail = true
      })
      assertThat(registry.strategies).isNotEmpty()

      assertThatThrownBy {
        generateFiles<TestDeclaration, TestContext, KotlinFileSpecStrategy<TestContext, TestDeclaration>>(
          input = testDeclaration,
          contextFactory = { TestContext(it.rootClassName, registry) }
        )
      }.isInstanceOf(IllegalStateException::class.java)
        .hasMessage(
          "No applicable strategy found/filtered for " +
            "`class io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecStrategy`."
        )
    }

    @Test
    fun `generate data class`() {
      registry.strategyList.add(TestDeclarationFileStrategy().apply {
        fail = false
      })

      assertThat(registry.strategies).isNotEmpty()

      val file = generateFiles<TestDeclaration, TestContext, KotlinFileSpecStrategy<TestContext, TestDeclaration>>(
        input = testDeclaration,
        contextFactory = { TestContext(it.rootClassName, registry) }
      ).single()

      assertThat(file.code).isEqualToIgnoringWhitespace(
        """
        package foo

        import kotlin.String

        public data class Bar(
          public val name: String,
        )
      """.trimIndent()
      )
    }
  }
}
