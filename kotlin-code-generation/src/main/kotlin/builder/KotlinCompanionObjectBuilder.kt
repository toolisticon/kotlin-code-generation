package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinCompanionObjectSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("Not implemented yet!")
class KotlinCompanionObjectBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinCompanionObjectSpec>(
  delegate = delegate
), TypeSpecSupplier {


  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinCompanionObjectSpec, KotlinCompanionObjectBuilder> {
    override fun invoke(spec: KotlinCompanionObjectSpec, kind: TypeSpec.Kind, name: String?): KotlinCompanionObjectBuilder = KotlinCompanionObjectBuilder(spec.get().toBuilder(kind,name))
  }

  override fun build(): KotlinCompanionObjectSpec = KotlinCompanionObjectSpec(spec = delegate.build())
}
