package io.toolisticon.kotlin.generation.itest.spi

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.strategy.KotlinDataClassSpecStrategy

@ExperimentalKotlinPoetApi
class TestDataClassStrategy : KotlinDataClassSpecStrategy<TestContext, MapInput>(contextType = TestContext::class, inputType = MapInput::class) {

  override fun invoke(context: TestContext, input: MapInput): KotlinDataClassSpec {
    return KotlinDataClassSpecBuilder.builder(input.className)
      .apply {
        input.forEach { name, type -> this.addConstructorProperty(name, type) }
      }
      .build()
  }
}
