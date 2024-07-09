package io.toolisticon.kotlin.generation.spi

/**
 * Root interface of all processors. Used to load all implementations
 * via ServiceLoader/SPI.
 */
interface KotlinCodeGenerationProcessor : KotlinCodeGenerationSpi
