package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
abstract class AbstractKotlinCodeGenerationProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any>(
  override val contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  override val builderType: KClass<BUILDER>,
  override val order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER> {

  override val name: String by lazy { this.javaClass.canonicalName }

  override fun toString(): String = name
}
