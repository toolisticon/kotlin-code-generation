package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation._BAK.KotlinConstructorProperty

class KotlinConstructorPropertyBuilder internal constructor(
  override val name: String,
  private val type: TypeName,
  private val propertyBuilder: KotlinPropertySpecBuilder,
  private val parameterBuilder: KotlinParameterSpecBuilder
) : Builder<KotlinConstructorProperty>, ConstructorPropertySupplier {

  companion object {
    fun builder(name: String, type: TypeName): KotlinConstructorPropertyBuilder = KotlinConstructorPropertyBuilder(
      name = name,
      type = type,
      propertyBuilder = KotlinPropertySpecBuilder.builder(name = name, type = type),
      parameterBuilder = KotlinParameterSpecBuilder.builder(name = name, type = type)
    )
  }


  fun makePrivate() = apply {
    propertyBuilder.makePrivate()
  }

  fun addAnnotation(annotationSpec: AnnotationSpec): KotlinConstructorPropertyBuilder = apply {
    parameterBuilder {
      addAnnotation(annotationSpec)
    }
  }

  override fun build(): KotlinConstructorProperty {
    val parameter = parameterBuilder.build()
    val property = propertyBuilder {
      initializer(parameter.name)
    }.build()

    return KotlinConstructorProperty(parameter = parameter, property = property)
  }

  override fun get(): KotlinConstructorProperty = build().get()
}
