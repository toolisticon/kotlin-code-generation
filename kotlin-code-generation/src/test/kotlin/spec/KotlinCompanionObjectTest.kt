package io.toolisticon.kotlin.generation.spec

import io.toolisticon.kotlin.generation.TestFixtures.notDeprecated
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test

@Deprecated("not implemented yet")
internal class KotlinCompanionObjectTest {
  @Test
  fun name() {
    assumeFalse(this::class.notDeprecated())
    TODO("Not yet implemented")
  }
}
