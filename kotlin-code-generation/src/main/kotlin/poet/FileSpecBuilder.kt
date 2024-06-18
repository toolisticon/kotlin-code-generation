package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.FileSpec

class FileSpecBuilder(
  override val builder: FileSpec.Builder
) : PoetSpecBuilder<FileSpecBuilder, FileSpec.Builder, FileSpec, FileSpecSupplier> {

  override fun invoke(block: FileSpecBuilderReceiver): FileSpecBuilder = apply {
    builder.block()
  }

  override fun build(): FileSpec = builder.build()
}

typealias FileSpecBuilderReceiver = FileSpec.Builder.() -> Unit
