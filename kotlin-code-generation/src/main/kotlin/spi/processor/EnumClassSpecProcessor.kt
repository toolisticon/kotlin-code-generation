package io.toolisticon.kotlin.generation.spi.processor

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.builder.KotlinEnumClassSpecBuilder
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
abstract class EnumClassSpecProcessor<CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any>(
  contextType: KClass<CONTEXT>,
  inputType: KClass<INPUT>,
  order: Int = KotlinCodeGenerationSpi.DEFAULT_ORDER
) : AbstractKotlinCodeGenerationProcessor<CONTEXT, INPUT, KotlinEnumClassSpecBuilder>(
  contextType = contextType,
  inputType = inputType,
  builderType = KotlinEnumClassSpecBuilder::class,
  order = order
)


