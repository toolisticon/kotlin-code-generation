package io.toolisticon.kotlin.generation.spi.registry

import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spi.*
import io.toolisticon.kotlin.generation.support.SuppressAnnotation
import mu.KLogging
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Holds all implementation instances of [KotlinCodeGenerationStrategy] and [KotlinCodeGenerationProcessor].
 *
 * Main Use-Case is loading these instances via [ServiceLoader], using [io.toolisticon.kotlin.generation.KotlinCodeGeneration.spi.repository].
 *
 * To avoid too many ´META-INF/services` declarations, all [KotlinCodeGenerationSpi] instances are declared in
 * one single resource. The loading mechanism automatically sorts them into [KotlinCodeGenerationSpiRegistryBean.strategies] and [KotlinCodeGenerationSpiRegistryBean.processors].
 */
class KotlinCodeGenerationServiceRepository(
  override val contextType: KClass<out KotlinCodeGenerationContext>,

  @PublishedApi
  internal val strategyMap: Map<String, KotlinCodeGenerationStrategy<*, *, *>>,

  @PublishedApi
  internal val processorMap: Map<String, KotlinCodeGenerationProcessor<*, *, *>>
) : KotlinCodeGenerationSpiRegistry {
  companion object : KLogging() {

    fun load(contextType: KClass<out KotlinCodeGenerationContext>, classLoader: ClassLoader = KotlinCodeGeneration.spi.defaultClassLoader()): KotlinCodeGenerationServiceRepository {
      val loadedSpis: List<KotlinCodeGenerationSpi<*, *>> = ServiceLoader.load(KotlinCodeGenerationSpi::class.java, classLoader).toList()
      require(loadedSpis.isNotEmpty()) { "No spis found, configure `${KotlinCodeGenerationSpi.metaInfServices}`." }

      val withIllegalContextType = loadedSpis.filterNot { it.contextType.isSubclassOf(contextType) }

      require(withIllegalContextType.isEmpty()) {
        "All declarations of type `${KotlinCodeGenerationSpi.metaInfServices}` " +
          "must be a subclass of contextType=$contextType, but " +
          "found ${withIllegalContextType.joinToString(", ")}."
      }

      return KotlinCodeGenerationServiceRepository(contextType = contextType, spiInstances = loadedSpis.map { it })
    }
  }

  constructor(contextType: KClass<out KotlinCodeGenerationContext>, vararg spiInstances: KotlinCodeGenerationSpi<*, *>) : this(contextType, spiInstances.toList())

  constructor(contextType: KClass<out KotlinCodeGenerationContext>, spiInstances: List<KotlinCodeGenerationSpi<*, *>>) : this(
    contextType = contextType,
    strategyMap = spiInstances.sorted().filterIsInstance<KotlinCodeGenerationStrategy<*, *, *>>().associateBy { it.name },
    processorMap = spiInstances.sorted().filterIsInstance<KotlinCodeGenerationProcessor<*, *, *>>().associateBy { it.name },
  )

  init {
    require(strategyMap.isNotEmpty()) { "At least one strategy is required." }
    if (processorMap.isEmpty()) {
      logger.info { "No processors have been registered." }
    }
  }

  override val processors: List<KotlinCodeGenerationProcessor<*, *, *>> = processorMap.values.sorted()
  override val strategies: List<KotlinCodeGenerationStrategy<*, *, *>> = strategyMap.values.sorted()

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext> findProcessors(subcontextType: KClass<CONTEXT>): List<KotlinCodeGenerationProcessor<CONTEXT, *, *>> {
    return processors.filter { subcontextType == it.contextType }.map { it as KotlinCodeGenerationProcessor<CONTEXT, *, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any> findProcessors(subcontextType: KClass<CONTEXT>, inputType: KClass<INPUT>): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, *>> {
    return processors.filter { subcontextType == it.contextType }.filter { it.inputType == inputType }.map { it as KotlinCodeGenerationProcessor<CONTEXT, INPUT, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any, BUILDER : Any> findProcessors(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
    builderType: KClass<BUILDER>
  ): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>> {
    return processors.filter { subcontextType == it.contextType }.filter { it.inputType == inputType }.filter { it.builderType == builderType }
      .map { it as KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext> findStrategies(subcontextType: KClass<CONTEXT>): List<KotlinCodeGenerationStrategy<CONTEXT, *, *>> {
    return strategies.filter { contextType == it.contextType }.map { it as KotlinCodeGenerationStrategy<CONTEXT, *, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext, INPUT : Any> findStrategies(subcontextType: KClass<CONTEXT>, inputType: KClass<INPUT>): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, *>> {
    return strategies.filter { contextType == it.contextType }
      .filter { inputType == it.inputType }
      .map { it as KotlinCodeGenerationStrategy<CONTEXT, INPUT, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <SUBCONTEXT : KotlinCodeGenerationContext, INPUT : Any, SPEC : Any> findStrategies(
    subcontextType: KClass<SUBCONTEXT>,
    inputType: KClass<INPUT>,
    specType: KClass<SPEC>
  ): List<KotlinCodeGenerationStrategy<SUBCONTEXT, INPUT, SPEC>> {
    return strategies.filter { contextType == it.contextType }
      .filter { inputType == it.inputType }
      .filter { specType == it.specType }
      .map { it as KotlinCodeGenerationStrategy<SUBCONTEXT, INPUT, SPEC> }
  }

  override fun toString(): String = "${this::class.simpleName}(" +
    "contextType=$contextType, " +
    "strategies=${strategyMap.keys}, " +
    "processors=${processorMap.keys})"

}
