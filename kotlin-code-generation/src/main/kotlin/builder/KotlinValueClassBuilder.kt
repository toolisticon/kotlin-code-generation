package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinValueClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinValueClassSpec>(
  delegate = delegate
), TypeSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinValueClassSpec, KotlinValueClassBuilder> {
    override fun invoke(spec: KotlinValueClassSpec, kind: TypeSpec.Kind, name: String?): KotlinValueClassBuilder = KotlinValueClassBuilder(spec.get().toBuilder(kind,name))

  }

  override fun build() = KotlinValueClassSpec(spec = delegate.build())
}
