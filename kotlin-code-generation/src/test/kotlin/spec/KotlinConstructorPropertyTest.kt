package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.TestFixtures.notDeprecated
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Test

@Deprecated("not implemented yet")
@ExperimentalKotlinPoetApi
internal class KotlinConstructorPropertyTest {
  @Test
  fun name() {
    assumeFalse(this::class.notDeprecated())
    TODO("Not yet implemented")
  }


}
