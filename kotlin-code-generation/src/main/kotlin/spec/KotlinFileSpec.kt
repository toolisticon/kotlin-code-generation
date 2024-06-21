package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.poet.FileSpecSupplier

data class KotlinFileSpec(
  private val spec: FileSpec
) : KotlinGeneratorSpec<KotlinFileSpec, FileSpec, FileSpecSupplier>, KotlinFileSpecSupplier {

  val packageName: String = spec.packageName
  val rootName: String = spec.name
  val className: ClassName = ClassName(packageName, rootName)
  val fqn: String = "$packageName.$rootName"
  val fileName: String = "$fqn.kt"

  override fun spec(): KotlinFileSpec = this
  override fun get(): FileSpec = spec
}

interface KotlinFileSpecSupplier : KotlinGeneratorSpecSupplier<KotlinFileSpec>, FileSpecSupplier
