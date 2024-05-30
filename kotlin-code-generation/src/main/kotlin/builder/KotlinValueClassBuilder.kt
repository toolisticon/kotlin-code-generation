package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import io.toolisticon.kotlin.generation.spec.ParameterSpecSupplier
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinValueClassBuilder internal constructor(className: ClassName, delegate: TypeSpec.Builder) : KotlinPoetNamedTypeSpecBuilder<KotlinValueClassSpec>(
  className = className,
  delegate = delegate
), TypeSpecSupplier {

  lateinit var constructorParameter: ParameterSpecSupplier

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinValueClassSpec, KotlinValueClassBuilder> {

    override fun invoke(spec: KotlinValueClassSpec, kind: TypeSpec.Kind, name: String?): KotlinValueClassBuilder = TODO() // FIXME KotlinValueClassBuilder(spec.get().toBuilder())
  }

  fun constructorParameter(constructorParameter: ParameterSpecSupplier) = apply {
    this.constructorParameter = constructorParameter
  }

  override fun build(): KotlinValueClassSpec {

    return KotlinValueClassSpec(
      className = className,
      spec = delegate.build()
    )
  }

}
