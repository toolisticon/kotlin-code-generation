package io.toolisticon.kotlin.generation.spi.registry

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpi
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationProcessor
import io.toolisticon.kotlin.generation.spi.UnboundKotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import java.util.ServiceLoader
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@ExperimentalKotlinPoetApi
class KotlinCodeGenerationServiceLoader(
  val contextTypeUpperBound: KClass<*> = Any::class,
  val classLoader: ClassLoader = KotlinCodeGeneration.spi.defaultClassLoader()
) : () -> KotlinCodeGenerationSpiRegistry {

  val metaInfServices = "META-INF/services/${KotlinCodeGenerationSpi::class.java.name}"

  override fun invoke(): KotlinCodeGenerationSpiRegistry {
    val serviceInstances: List<KotlinCodeGenerationSpi<*, *>> = ServiceLoader.load(KotlinCodeGenerationSpi::class.java, classLoader).toList()
    require(serviceInstances.isNotEmpty()) { "No serviceInstances found, configure `${KotlinCodeGenerationSpi.metaInfServices}`." }

    val withIllegalContextType = serviceInstances.filterNot { it.contextType.isSubclassOf(contextTypeUpperBound) }

    require(withIllegalContextType.isEmpty()) {
      "All declarations of type `${KotlinCodeGenerationSpi.metaInfServices}` " +
        "must be a subclass of contextType=$contextTypeUpperBound, but " +
        "found ${withIllegalContextType.joinToString(", ")}."
    }

    return KotlinCodeGenerationServiceRepository(
      contextTypeUpperBound = contextTypeUpperBound,
      strategies = KotlinCodeGenerationStrategyList(serviceInstances.filterIsInstance<UnboundKotlinCodeGenerationStrategy>()),
      processors = KotlinCodeGenerationProcessorList(serviceInstances.filterIsInstance<UnboundKotlinCodeGenerationProcessor>())
    )
  }
}
