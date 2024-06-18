package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeSpec

class FileSpecBuilder(
  override val builder: FileSpec.Builder
) : PoetSpecBuilder<FileSpecBuilder, FileSpec.Builder, FileSpec, FileSpecSupplier> {
  companion object {
    fun FileSpec.Builder.wrap() = FileSpecBuilder(this)

    @JvmStatic
    fun get(packageName: String, typeSpec: TypeSpec): FileSpec = FileSpec.get(packageName, typeSpec)

    @JvmStatic
    fun builder(className: ClassName): FileSpecBuilder = FileSpec.builder(className).wrap()

    @JvmStatic
    fun builder(memberName: MemberName): FileSpecBuilder = FileSpec.builder(memberName).wrap()

    @JvmStatic
    fun builder(packageName: String, fileName: String): FileSpecBuilder = FileSpec.builder(packageName, fileName).wrap()

    @JvmStatic
    fun scriptBuilder(fileName: String, packageName: String = ""): FileSpecBuilder = FileSpec.scriptBuilder(fileName, packageName).wrap()
  }

  override fun invoke(block: FileSpecBuilderReceiver): FileSpecBuilder = apply {
    builder.block()
  }

  override fun build(): FileSpec = builder.build()
}

typealias FileSpecBuilderReceiver = FileSpec.Builder.() -> Unit
