
package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.TestFixtures.notDeprecated
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Test

@OptIn(ExperimentalKotlinPoetApi::class)
@Deprecated("not implemented yet")
internal class KotlinObjectTest {
  @Test
  fun name() {
    assumeFalse(this::class.notDeprecated())
    TODO("Not yet implemented")
  }
}
