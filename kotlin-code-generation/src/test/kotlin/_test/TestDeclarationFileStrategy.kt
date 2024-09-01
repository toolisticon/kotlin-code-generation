@file:OptIn(ExperimentalKotlinPoetApi::class)

package io.toolisticon.kotlin.generation._test

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildDataClass
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFile
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec
import io.toolisticon.kotlin.generation.spi.strategy.KotlinFileSpecStrategy

class TestDeclarationFileStrategy : KotlinFileSpecStrategy<TestContext, TestDeclaration>(
  contextType = TestContext::class, TestDeclaration::class
) {

  var fail = false

  override fun invoke(context: TestContext, input: TestDeclaration): KotlinFileSpec = buildFile(context.rootClassName) {
    addType(buildDataClass(this.className) {
      input.testInput.map.forEach {
        addConstructorProperty(it.key, it.value)
      }
    })
  }

  override fun test(context: TestContext, input: Any): Boolean {
    return super.test(context, input) && !fail
  }
}
