package io.toolisticon.kotlin.generation.spi.registry

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiListSupplier
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import java.util.*

/**
 * Provides [KotlinCodeGenerationSpiRegistry] using [ServiceLoader].
 *
 * To avoid too many ´META-INF/services` declarations, all [KotlinCodeGenerationSpi] instances are declared in
 * one single resource. The loading mechanism automatically sorts them into strategies and processors.
 *
 * @since 0.2.0: does not filter on context type anymore, so all services are accepted regardless of their context type.
 */
@ExperimentalKotlinPoetApi
class KotlinCodeGenerationServiceLoader(
  val classLoader: ClassLoader = KotlinCodeGeneration.spi.defaultClassLoader()
) : KotlinCodeGenerationSpiListSupplier {

  override fun invoke(): KotlinCodeGenerationSpiList = KotlinCodeGenerationSpiList(
    ServiceLoader.load(KotlinCodeGenerationSpi::class.java, classLoader).toList()
  )
}
