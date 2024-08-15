package io.toolisticon.kotlin.generation.spi.processor

import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

abstract class ConstructorPropertySpecProcessor<CONTEXT : KotlinCodeGenerationContext, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : AbstractKotlinCodeGenerationProcessor<CONTEXT, INPUT, KotlinConstructorPropertySpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  order = order,
  builderType = KotlinConstructorPropertySpecBuilder::class
)


