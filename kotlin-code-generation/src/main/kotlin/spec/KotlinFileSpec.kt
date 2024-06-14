package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.FileSpecSupplier

data class KotlinFileSpec(private val spec: FileSpec) : FileSpecSupplier {

  val packageName: String = spec.packageName
  val rootName: String = spec.name
  val className: ClassName = ClassName(packageName, rootName)
  val fqn: String = "$packageName.$rootName"
  val fileName: String = "$fqn.kt"

  override fun get(): FileSpec = spec
}
