package io.toolisticon.kotlin.generation.spi.strategy

import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

abstract class DataClassSpecStrategy<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  override val inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER,
) : AbstractKotlinCodeGenerationStrategy<CONTEXT, INPUT, KotlinDataClassSpec>(
  contextType = contextType,
  inputType = inputType,
  specType = KotlinDataClassSpec::class,
  order = order
)
