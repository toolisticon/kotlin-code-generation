package io.toolisticon.kotlin.generation.spi.registry

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spi.*
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import io.toolisticon.kotlin.generation.support.SuppressAnnotation
import mu.KLogging
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Holds all implementation instances of [KotlinCodeGenerationStrategy] and [KotlinCodeGenerationProcessor].
 *
 * Main Use-Case is loading these instances via [ServiceLoader], using [io.toolisticon.kotlin.generation.KotlinCodeGeneration.spi.registry].
 *
 * To avoid too many ´META-INF/services` declarations, all [KotlinCodeGenerationSpi] instances are declared in
 * one single resource. The loading mechanism automatically sorts them into strategies and processors.
 */
@ExperimentalKotlinPoetApi
class KotlinCodeGenerationServiceRepository(
  override val contextTypeUpperBound: KClass<*>,
  override val processors: KotlinCodeGenerationProcessorList<*, *, *>,
  override val strategies: KotlinCodeGenerationStrategyList<*, *, *>,
) : KotlinCodeGenerationSpiRegistry {
  companion object : KLogging() {

    fun load(contextTypeUpperBound: KClass<*>, classLoader: ClassLoader = KotlinCodeGeneration.spi.defaultClassLoader()): KotlinCodeGenerationServiceRepository {
      val serviceInstances: List<KotlinCodeGenerationSpi<*, *>> = ServiceLoader.load(KotlinCodeGenerationSpi::class.java, classLoader).toList()
      require(serviceInstances.isNotEmpty()) { "No serviceInstances found, configure `${KotlinCodeGenerationSpi.metaInfServices}`." }

      val withIllegalContextType = serviceInstances.filterNot { it.contextType.isSubclassOf(contextTypeUpperBound) }

      require(withIllegalContextType.isEmpty()) {
        "All declarations of type `${KotlinCodeGenerationSpi.metaInfServices}` " +
          "must be a subclass of contextType=$contextTypeUpperBound, but " +
          "found ${withIllegalContextType.joinToString(", ")}."
      }

      val strategies: KotlinCodeGenerationStrategyList<*,*,*>  = KotlinCodeGenerationStrategyList.of(serviceInstances)

      return KotlinCodeGenerationServiceRepository(
        contextTypeUpperBound = contextTypeUpperBound,
        strategies = KotlinCodeGenerationStrategyList.of(serviceInstances)
        )
    }

    fun <CONTEXT : KotlinCodeGenerationContext<CONTEXT>, INPUT : Any, SPEC : Any, BUILDER : Any> of(serviceInstances: List<KotlinCodeGenerationSpi<*, *>>): KotlinCodeGenerationProcessorList<CONTEXT, INPUT, SPEC> {
      val strategies : KotlinCodeGenerationStrategyList<*,*,*> = KotlinCodeGenerationStrategyList.of(se
        value = spi.sorted()
          .filterIsInstance<KotlinCodeGenerationProcessor<CONTEXT, INPUT, SPEC>>()
      )
      val processors = KotlinCodeGenerationProcessorList(
        value = spi.sorted()
          .filterIsInstance<KotlinCodeGenerationProcessor<CONTEXT, INPUT, SPEC>>()
      )


      return KotlinCodeGenerationProcessorList(value)
    }
  }

  constructor(contextTypeUpperBound: KClass<*>, vararg spiInstances: KotlinCodeGenerationSpi<*, *>) : this(contextTypeUpperBound, spiInstances.toList())

  init {
    require(strategies.isNotEmpty()) { "At least one strategy is required." }
    if (processors.isEmpty()) {
      logger.info { "No processors have been registered." }
    }
  }

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
  ): KotlinCodeGenerationStrategyList<CONTEXT, INPUT, SPEC> {
    return KotlinCodeGenerationStrategyList(strategies
      .filter { it.matchesContextType(subcontextType) }
      .filter { it.matchesInputType(inputType) }
      .filter { it.matchesSpecType(subcontextType) }
      .map { it as KotlinCodeGenerationStrategy<CONTEXT, INPUT, SPEC> })
  }

  override fun toString(): String = "${this::class.simpleName}(" +
    "contextType=$contextTypeUpperBound, " +
    "strategies=${strategies}, " +
    "processors=${processors})"

}
