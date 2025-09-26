package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.codeBlock.SPACE
import io.toolisticon.kotlin.generation.poet.CodeBlockBuilder.Companion.codeBlock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
class CodeBlockBuilderTest {

  @Test
  fun `empty code block`() {
    assertThat(CodeBlockBuilder.EMPTY_CODE_BLOCK.isEmpty()).isTrue()
  }

  @Test
  fun `add all blocks with separator`() {
    val block = KotlinCodeGeneration.buildCodeBlock {
      addAll(blocks = listOf(codeBlock("return"), codeBlock("1")), separator = SPACE)
    }
    assertThat(block).hasToString("return 1")
  }
}
