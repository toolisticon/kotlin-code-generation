package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec

@JvmInline
value class KotlinFileSpec(override val spec: FileSpec) : KotlinPoetSpec<FileSpec>, FileSpecSupplier {

  val packageName: String get() = spec.packageName
  val rootName: String get() = spec.name
  val className: ClassName get() = ClassName(packageName, rootName)
  val fqn: String get() = "$packageName.$rootName"
  val fileName: String get() = "$fqn.kt"

  override fun get(): FileSpec = spec
}
