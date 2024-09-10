
package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.context.KotlinCodeGenerationContextBase
import io.toolisticon.kotlin.generation.spi.processor.KotlinCodeGenerationProcessorList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinCodeGenerationStrategyList
import io.toolisticon.kotlin.generation.spi.strategy.KotlinDataClassSpecStrategy
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLClassLoader
import java.net.URLConnection
import java.net.URLStreamHandler
import java.time.Instant
import java.util.*
import kotlin.reflect.KClass

@OptIn(ExperimentalKotlinPoetApi::class)
object TestFixtures {

  // a fixed instant to be used in test with
  val NOW = Instant.parse("2024-07-02T10:01:33.205357100Z")

  @Target(allowedTargets = [AnnotationTarget.VALUE_PARAMETER])
  annotation class MyAnnotation(
    val name: String,
    val type: KClass<*>
  )

  val myAnnotationSpec = buildAnnotation(MyAnnotation::class)

  fun KClass<*>.notDeprecated() = !this.annotations.map { it::class }.contains(Deprecated::class)


  /**
   * Testing classloader for ServiceLoader.
   *
   * This classloader overrides the META-INF/services/<interface> file with a custom definition.
   *
   * Constructs a fake META-INF/services/<metaInfInterface> file which contains the provided array of classes.
   * When the implementingClasses array is null, the META-INF file will not be constructed.
   * The classes from implementingClasses are not required to implement the metaInfInterface.
   *
   * @param metaInfInterface    ServiceLoader interface
   * @param implementingClasses potential subclasses of the ServiceLoader metaInfInterface
   */
  class ServiceLoaderTestClassLoader(
    val metaInfInterface: KClass<*>,
    val implementingClasses: List<String>
  ) : URLClassLoader(arrayOfNulls<URL>(0), metaInfInterface.java.classLoader) {

    constructor(metaInfInterface: KClass<*>, vararg implementingClasses: KClass<*>) : this(
      metaInfInterface = metaInfInterface,
      implementingClasses = implementingClasses.toList().map { it.java.name }
    )

    init {
      require(metaInfInterface.java.isInterface) { "the META-INF service $metaInfInterface must be an interface" }
    }

    val fileContent: String = implementingClasses.joinToString(separator = "\n")

    @Throws(IOException::class)
    override fun getResources(name: String): Enumeration<URL> {
      if (name == "META-INF/services/" + metaInfInterface.java.name) {
        if (implementingClasses.isEmpty()) {
          return Collections.emptyEnumeration()
        }

        val url: URL = URL("foo", "bar", 99, "/foobar", object : URLStreamHandler() {
          override fun openConnection(u: URL?): URLConnection {
            return object : URLConnection(u) {
              override fun connect() {
              }

              @Throws(IOException::class)
              override fun getInputStream(): InputStream = fileContent.byteInputStream()
            }
          }
        })

        return object : Enumeration<URL> {
          var hasNext: Boolean = true

          override fun hasMoreElements(): Boolean {
            return hasNext
          }

          override fun nextElement(): URL {
            hasNext = false
            return url
          }
        }
      }
      return super.getResources(name)
    }
  }


  object SpiFixtures {

    data class InputA(
      val packageName: PackageName,
      val simpleName: SimpleName,
      val fields: Map<String, KClass<*>>
    ) {
      val className = KotlinCodeGeneration.className(packageName, simpleName)
    }

    data class InputB(
      val className: ClassName,
      val fields: Map<String, KClass<*>>
    )

    class SpiTestContext(registry: KotlinCodeGenerationSpiRegistry) : KotlinCodeGenerationContextBase<SpiTestContext>(registry) {
      override val contextType = SpiTestContext::class
    }

    abstract class DataClassAStrategy : KotlinDataClassSpecStrategy<EmptyContext, InputA>(
      contextType = EmptyContext::class, inputType = InputA::class
    )

    abstract class DataClassBStrategy : KotlinDataClassSpecStrategy<EmptyContext, InputB>(
      contextType = EmptyContext::class, inputType = InputB::class
    )


    object EmptyContext : KotlinCodeGenerationContext<EmptyContext> {
      override val contextType = EmptyContext::class
      override val registry = EmptyRegistry
    }

    object EmptyRegistry : KotlinCodeGenerationSpiRegistry {
      override val contextTypeUpperBound = Any::class
      override val strategies: KotlinCodeGenerationStrategyList = KotlinCodeGenerationStrategyList()
      override val processors: KotlinCodeGenerationProcessorList = KotlinCodeGenerationProcessorList()
    }
  }

}
