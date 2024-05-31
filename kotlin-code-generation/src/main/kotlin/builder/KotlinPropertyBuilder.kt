package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec
import io.toolisticon.kotlin.generation.spec.ParameterSpecSupplier
import io.toolisticon.kotlin.generation.spec.PropertySpecSupplier

class KotlinPropertyBuilder internal constructor(
  private val name: String,
  private val type: TypeName,
  delegate: PropertySpec.Builder
) : KotlinPoetSpecBuilder<KotlinPropertyBuilder, KotlinPropertySpec,
  PropertySpec,
  PropertySpec.Builder>(
  delegate = delegate
), PropertySpecSupplier, KotlinAnnotatableBuilder<KotlinPropertyBuilder> {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetSpecBuilder<KotlinPropertySpec, KotlinPropertyBuilder> {

    operator fun invoke(name: String, type: TypeName, block: PropertySpecBuilderReceiver = {}) = KotlinPropertyBuilder(
      name = name,
      type = type,
      delegate = PropertySpec.builder(name, type)
    ).invoke(block)

    override fun invoke(spec: KotlinPropertySpec): KotlinPropertyBuilder = KotlinPropertyBuilder(
      name = spec.name,
      type = spec.type,
      delegate = spec.get().toBuilder()
    )
  }

  override fun addAnnotation(annotationSpec: AnnotationSpec): KotlinPropertyBuilder = invoke {
    addAnnotation(annotationSpec)
  }

  fun makePrivate() = invoke {
    addModifiers(KModifier.PRIVATE)
  }

  fun makeFinal() = invoke {
    addModifiers(KModifier.PRIVATE)
  }

  override fun build(): KotlinPropertySpec = KotlinPropertySpec(spec = delegate.build())

  override fun get(): PropertySpec = build().get()

}
