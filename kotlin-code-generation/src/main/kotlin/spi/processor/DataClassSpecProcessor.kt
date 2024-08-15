package io.toolisticon.kotlin.generation.spi.processor

import io.toolisticon.kotlin.generation.builder.KotlinDataClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

abstract class DataClassSpecProcessor<CONTEXT : KotlinCodeGenerationContext, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : AbstractKotlinCodeGenerationProcessor<CONTEXT, INPUT, KotlinDataClassSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  builderType = KotlinDataClassSpecBuilder::class,
  order = order
)



