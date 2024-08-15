package io.toolisticon.kotlin.generation.spi

import kotlin.reflect.KClass

/**
 * Context used for SPI execution. Typically, holds mutable state that is modified while processing the chain.
 * Required because all spi-instances have to be generated via default-constructor, so all state data
 * we would normally store in a property has to be passed to every function call.
 */
interface KotlinCodeGenerationContext {

  /**
   * Include the [KotlinCodeGenerationSpiRegistryBean] so strategies and processors can access themselves if required.
   */
  val registry: AbstractKotlinCodeGenerationSpiRegistry
  val contextType: KClass<out KotlinCodeGenerationContext>

}
