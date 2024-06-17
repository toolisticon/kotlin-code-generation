package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorProperty

class KotlinConstructorPropertyBuilder internal constructor(
  val name: String,
  private val type: TypeName,
  private val propertyBuilder: KotlinPropertySpecBuilder,
  private val parameterBuilder: KotlinParameterSpecBuilder
) : Builder<KotlinConstructorProperty>, ConstructorPropertySupplier {

//  companion object {
//    fun builder(name: String, type: TypeName): KotlinConstructorPropertyBuilder = KotlinConstructorPropertyBuilder(
//      name = name,
//      type = type,
//      propertyBuilder = KotlinPropertySpecBuilder.builder(name = name, type = type),
//      parameterBuilder = KotlinParameterSpecBuilder.builder(name = name, type = type)
//    )
//  }

//
//  fun makePrivate() = apply {
//    propertyBuilder.makePrivate()
//  }
//
//  fun addAnnotation(annotationSpec: AnnotationSpec): KotlinConstructorPropertyBuilder = apply {
//    parameterBuilder {
//      addAnnotation(annotationSpec)
//    }
//  }

  override fun build(): KotlinConstructorProperty {
    val parameter = parameterBuilder.build()
    val property = propertyBuilder
      .builder {
        initializer(parameter.name)
      }
      .build()

    return KotlinConstructorProperty(parameter = parameter, property = property)
  }

  override fun get(): KotlinConstructorProperty = build().get()
}
