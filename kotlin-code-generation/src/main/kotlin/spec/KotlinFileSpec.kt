package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.WithClassName
import io.toolisticon.kotlin.generation.poet.FileSpecSupplier
import kotlin.reflect.KClass

/**
 * Represents a file.
 */
data class KotlinFileSpec(
  private val spec: FileSpec
) : KotlinGeneratorSpec<KotlinFileSpec, FileSpec, FileSpecSupplier>, KotlinFileSpecSupplier, TaggableSpec, KotlinFileSpecIterable {

  val packageName: String = spec.packageName
  val rootName: String = spec.name
  override fun <T : Any> tag(type: KClass<T>): T? = get().tag(type)
  override val className: ClassName = ClassName(packageName, rootName)
  val fqn: String = "$packageName.$rootName"
  val fileName: String = "$fqn.kt"

  override fun iterator(): Iterator<KotlinFileSpec> = listOf(this).listIterator()

  override fun spec(): KotlinFileSpec = this
  override fun get(): FileSpec = spec
}

/**
 * Marks the builder and the spec so they are interchangeable.
 */
interface KotlinFileSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFileSpec>, FileSpecSupplier, WithClassName {
  override fun get(): FileSpec = spec().get()
}

/**
 * List that contains multiple [KotlinFileSpec]s.
 */
@JvmInline
value class KotlinFileSpecList(private val fileSpecs: List<KotlinFileSpec>) : List<KotlinFileSpec> by fileSpecs, KotlinFileSpecIterable {
  companion object {

    /**
     * Empty Specs.
     */
    val EMPTY = KotlinFileSpecList(emptyList())

    /**
     * Create [KotlinFileSpecList] from given vararg iterables.
     */
    fun of(vararg iterables: KotlinFileSpecIterable) = of(iterables.toList())


    /**
     * Create [KotlinFileSpecList] from given iterables.
     */
    fun of(iterables: List<KotlinFileSpecIterable>) = KotlinFileSpecList(iterables.flatten())

    /**
     * Collect file spec suppliers
     */
    fun collect(vararg fns: () -> KotlinFileSpec) = collect(fns.toList())

    /**
     * Collect file spec suppliers.
     */
    fun collect(specs: List<() -> KotlinFileSpec>) = specs.fold(EMPTY) { acc, cur -> acc + cur() }
  }

  /**
   * Create new instance from single spec.
   */
  constructor(fileSpec: KotlinFileSpec) : this(listOf(fileSpec))

  /**
   * Create copy of this list and add new spec(s).
   */
  operator fun plus(fileSpec: KotlinFileSpecIterable) = KotlinFileSpecList(fileSpecs + fileSpec)
}

/**
 * Iterable that provides instances of [KotlinFileSpec].
 */
sealed interface KotlinFileSpecIterable : Iterable<KotlinFileSpec>
