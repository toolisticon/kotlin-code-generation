package io.toolisticon.kotlin.generation.spec

import io.toolisticon.kotlin.generation.TestFixtures.notDeprecated
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Test

@Deprecated("not implemented yet")
internal class KotlinAnonymousClassTest {
  @Test
  fun name() {
    assumeFalse(this::class.notDeprecated())
    TODO("Not yet implemented")
  }
}
