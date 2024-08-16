package io.toolisticon.kotlin.generation.spi.registry

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
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
 * one single resource. The loading mechanism automatically sorts them into strategies and processors.
 */
@ExperimentalKotlinPoetApi
class KotlinCodeGenerationServiceRepository(
  override val contextTypeUpperBound: KClass<*>,

  @PublishedApi
  internal val strategyMap: Map<String, KotlinCodeGenerationStrategy<*, *, *>>,

  @PublishedApi
  internal val processorMap: Map<String, KotlinCodeGenerationProcessor<*, *, *>>
) : KotlinCodeGenerationSpiRegistry {
  companion object : KLogging() {

    fun load(contextType: KClass<*>, classLoader: ClassLoader = KotlinCodeGeneration.spi.defaultClassLoader()): KotlinCodeGenerationServiceRepository {
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

  constructor(contextType: KClass<*>, vararg spiInstances: KotlinCodeGenerationSpi<*, *>) : this(contextType, spiInstances.toList())

  constructor(contextType: KClass<*>, spiInstances: List<KotlinCodeGenerationSpi<*, *>>) : this(
    contextTypeUpperBound = contextType,
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
  override fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>> findProcessors(subcontextType: KClass<CONTEXT>): List<KotlinCodeGenerationProcessor<CONTEXT, *, *>> {
    return processors.filter { subcontextType == it.contextType }.map { it as KotlinCodeGenerationProcessor<CONTEXT, *, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> findProcessors(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>
  ): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, *>> {
    return processors.filter { subcontextType == it.contextType }.filter { it.inputType == inputType }.map { it as KotlinCodeGenerationProcessor<CONTEXT, INPUT, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, BUILDER : Any> findProcessors(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
    builderType: KClass<BUILDER>
  ): List<KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER>> {
    return processors.filter { subcontextType == it.contextType }.filter { it.inputType == inputType }.filter { it.builderType == builderType }
      .map { it as KotlinCodeGenerationProcessor<CONTEXT, INPUT, BUILDER> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>> findStrategies(subcontextType: KClass<CONTEXT>): List<KotlinCodeGenerationStrategy<CONTEXT, *, *>> {
    return strategies.filter { subcontextType == it.contextType }.map { it as KotlinCodeGenerationStrategy<CONTEXT, *, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any> findStrategies(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, *>> {
    return strategies.filter { subcontextType == it.contextType }
      .filter { inputType == it.inputType }
      .map { it as KotlinCodeGenerationStrategy<CONTEXT, INPUT, *> }
  }

  @Suppress(SuppressAnnotation.UNCHECKED_CAST)
  override fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any> findStrategies(
    subcontextType: KClass<CONTEXT>,
    inputType: KClass<INPUT>,
    specType: KClass<SPEC>
  ): List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC>> {
    return strategies.filter { subcontextType == it.contextType }
      .filter { inputType == it.inputType }
      .filter { specType == it.specType }
      .map { it as KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC> }
  }

  override fun toString(): String = "${this::class.simpleName}(" +
    "contextType=$contextTypeUpperBound, " +
    "strategies=${strategyMap.keys}, " +
    "processors=${processorMap.keys})"

}
