package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.WithClassName
import io.toolisticon.kotlin.generation.poet.FileSpecSupplier

data class KotlinFileSpec(
  private val spec: FileSpec
) : KotlinGeneratorSpec<KotlinFileSpec, FileSpec, FileSpecSupplier>, KotlinFileSpecSupplier {

  val packageName: String = spec.packageName
  val rootName: String = spec.name
  override val className: ClassName = ClassName(packageName, rootName)
  val fqn: String = "$packageName.$rootName"
  val fileName: String = "$fqn.kt"

  override fun spec(): KotlinFileSpec = this
  override fun get(): FileSpec = spec
}

interface KotlinFileSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFileSpec>, FileSpecSupplier, WithClassName {
  override fun get(): FileSpec = spec().get()
}

/**
 * List that contains multiple [KotlinFileSpec]s.
 */
@JvmInline
value class KotlinFileSpecs(private val fileSpecs: List<KotlinFileSpec>) : List<KotlinFileSpec> by fileSpecs {
  companion object {
    val EMPTY = KotlinFileSpecs(emptyList())

    fun collect(vararg fns: () -> KotlinFileSpec) = fns.fold(EMPTY) { acc, cur -> acc + cur() }
  }

  constructor(fileSpec : KotlinFileSpec) : this(listOf(fileSpec))

  operator fun plus(fileSpec : KotlinFileSpec): KotlinFileSpecs = KotlinFileSpecs(fileSpecs + fileSpec)
}
