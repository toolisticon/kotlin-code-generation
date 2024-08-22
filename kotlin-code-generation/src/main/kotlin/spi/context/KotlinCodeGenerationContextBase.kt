package io.toolisticon.kotlin.generation.spi.context

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import kotlin.reflect.KClass

/**
 * Abstract base implementation of [KotlinCodeGenerationContext] for convenience setting of generic types.
 */
@ExperimentalKotlinPoetApi
abstract class KotlinCodeGenerationContextBase<SELF : KotlinCodeGenerationContext<SELF>>(
  override val registry: KotlinCodeGenerationSpiRegistry
) : KotlinCodeGenerationContext<SELF> {
  abstract override val contextType: KClass<SELF>
}
