package io.toolisticon.kotlin.generation.poet

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CodeBlockBuilderTest {

  @Test
  fun `empty code block`() {
    assertThat(CodeBlockBuilder.EMPTY_CODE_BLOCK.isEmpty()).isTrue()
  }
}
