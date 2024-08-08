package io.toolisticon.kotlin.generation.spi

import io.toolisticon.kotlin.generation.support.SuppressAnnotation.Companion.UNCHECKED_CAST
import mu.KLogging
import java.util.*
import kotlin.reflect.KClass

/**
 * Holds all implementation instances of [KotlinCodeGenerationStrategy] and [KotlinCodeGenerationProcessor].
 *
 * Main Use-Case is loading these instances via [ServiceLoader], using [KotlinCodeGenerationSpiRegistry.load].
 *
 * To avoid too many ´META-INF/services` declarations, all [KotlinCodeGenerationSpi] instances are declared in
 * one single resource. The loading mechanism automatically sorts them into [KotlinCodeGenerationSpiRegistry.strategies] and [KotlinCodeGenerationSpiRegistry.processors].
 */
open class KotlinCodeGenerationSpiRegistry<CONTEXT : KotlinCodeGenerationContext<CONTEXT>>(
  val contextType: KClass<CONTEXT>,
  val strategies: Map<String, KotlinCodeGenerationStrategy<CONTEXT, *, *>>,
  val processors: Map<String, KotlinCodeGenerationProcessor<CONTEXT, *, *>>
) {
  companion object : KLogging() {
    val metaInfServices = "META-INF/services/${KotlinCodeGenerationSpi::class.qualifiedName}"

    @Suppress(UNCHECKED_CAST)
    inline fun <reified CONTEXT : KotlinCodeGenerationContext<CONTEXT>> load(): KotlinCodeGenerationSpiRegistry<CONTEXT> {
      val contextType = CONTEXT::class
      val loadedSpis: List<KotlinCodeGenerationSpi<*, *>> = ServiceLoader.load(KotlinCodeGenerationSpi::class.java).toList()

      require(loadedSpis.isNotEmpty()) { "No spis found, configure `$metaInfServices`." }

      val withIllegalContextType = loadedSpis.filterNot { contextType == it.contextType }

      require(withIllegalContextType.isEmpty()) { "All declarations of type `$metaInfServices` must match contextType=$contextType, but found ${withIllegalContextType.joinToString(", ")}." }

      return KotlinCodeGenerationSpiRegistry(contextType = contextType, spiInstances = loadedSpis.map { it as KotlinCodeGenerationSpi<CONTEXT, *> })
    }
  }

  constructor(contextType: KClass<CONTEXT>, vararg spiInstances: KotlinCodeGenerationSpi<CONTEXT, *>) : this(contextType, spiInstances.toList())

  constructor(contextType: KClass<CONTEXT>, spiInstances: List<KotlinCodeGenerationSpi<CONTEXT, *>>) : this(
    contextType = contextType,
    strategies = spiInstances.sorted().filterIsInstance<KotlinCodeGenerationStrategy<CONTEXT, *, *>>().associateBy { it.name },
    processors = spiInstances.sorted().filterIsInstance<KotlinCodeGenerationProcessor<CONTEXT, *, *>>().associateBy { it.name },
  )

  init {
    require(strategies.isNotEmpty()) { "At least one strategy is required." }
    if (processors.isEmpty()) {
      logger.info { "No processors have been registered." }
    }
  }

  @Suppress(UNCHECKED_CAST)
  inline fun <reified INPUT : Any, reified SPEC : Any> getStrategy(): KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC> {
    val strategy = requireNotNull(strategies.values.find { it.specType == SPEC::class }) { "No Strategy found for targetSpec=${SPEC::class}." }

    return strategy as KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>
  }

  override fun toString(): String = "${this::class.simpleName}(" +
    "contextType=$contextType, " +
    "strategies=${strategies.keys}, " +
    "processors=${processors.keys})"

}
