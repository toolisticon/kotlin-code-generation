package io.toolisticon.kotlin.generation

import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLClassLoader
import java.net.URLConnection
import java.net.URLStreamHandler
import java.time.Instant
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.reflect.KClass


object TestFixtures {

  // a fixed instant to be used in test with
  val NOW = Instant.parse( "2024-07-02T10:01:33.205357100Z")

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

    val fileContent : String = implementingClasses.joinToString(separator = "\n")

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
}
