package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FileSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.FileSpecSupplier
import io.toolisticon.kotlin.generation.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFileSpec

class KotlinFileSpecBuilder internal constructor(
  private val delegate: FileSpecBuilder
) : BuilderSupplier<KotlinFileSpec, FileSpec>, FileSpecSupplier, DelegatingBuilder<KotlinFileSpecBuilder, FileSpecBuilderReceiver> {


  override fun builder(block: FileSpecBuilderReceiver) = apply {
    delegate { block() }
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate {
      addType(typeSpecSupplier.get())
    }
  }
  
  override fun build(): KotlinFileSpec {
    val spec = delegate.build()
    return KotlinFileSpec(spec = spec)
  }

  override fun get(): FileSpec = build().get()
}
