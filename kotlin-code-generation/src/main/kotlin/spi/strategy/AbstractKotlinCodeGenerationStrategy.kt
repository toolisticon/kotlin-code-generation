package io.toolisticon.kotlin.generation.spi.strategy

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
abstract class AbstractKotlinCodeGenerationStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any>(
  override val contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  override val specType: KClass<SPEC>,
  override val order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC> {

  override val name: String by lazy { javaClass.canonicalName }

  override fun toString(): String = name
}
