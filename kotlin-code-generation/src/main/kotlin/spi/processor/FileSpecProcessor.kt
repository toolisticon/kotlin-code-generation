package io.toolisticon.kotlin.generation.spi.processor

import io.toolisticon.kotlin.generation.builder.KotlinFileSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

abstract class FileSpecProcessor<CONTEXT : KotlinCodeGenerationContext, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : AbstractKotlinCodeGenerationProcessor<CONTEXT, INPUT, KotlinFileSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  builderType = KotlinFileSpecBuilder::class,
  order = order
)


