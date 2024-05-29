package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinObjectSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinObjectBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinObjectSpec>(
  delegate = delegate
), TypeSpecSupplier {


  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinObjectSpec, KotlinObjectBuilder> {

    operator fun invoke(className: ClassName) = KotlinObjectBuilder(delegate = TypeSpec.objectBuilder(className))

    override fun invoke(spec: KotlinObjectSpec, kind: TypeSpec.Kind, name: String?): KotlinObjectBuilder = KotlinObjectBuilder(spec.get().toBuilder(kind, name))
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun addTypes(typeSpecSupplier: Iterable<TypeSpecSupplier>) = apply {
    typeSpecSupplier.forEach(::addType)
  }

  override fun build(): KotlinObjectSpec = KotlinObjectSpec(spec = delegate.build())
}
