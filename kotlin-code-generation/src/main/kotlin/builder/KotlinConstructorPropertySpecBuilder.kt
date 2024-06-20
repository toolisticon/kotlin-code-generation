package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpec
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier

class KotlinConstructorPropertySpecBuilder internal constructor(
  val name: String,
  private val type: TypeName,
  private val propertyBuilder: KotlinPropertySpecBuilder,
  private val parameterBuilder: KotlinParameterSpecBuilder
) : Builder<KotlinConstructorPropertySpec>, KotlinConstructorPropertySpecSupplier {

  companion object {
    fun builder(name: String, type: TypeName): KotlinConstructorPropertySpecBuilder = KotlinConstructorPropertySpecBuilder(
      name = name,
      type = type,
      propertyBuilder = KotlinPropertySpecBuilder.builder(name = name, type = type),
      parameterBuilder = KotlinParameterSpecBuilder.builder(name = name, type = type)
    )
  }

  fun makePrivate() = apply {
    propertyBuilder.builder {
      addModifiers(KModifier.PRIVATE)
    }
  }

  fun addAnnotation(annotationSpec: KotlinAnnotationSpecSupplier) = apply {
    parameterBuilder.addAnnotation(annotationSpec)
  }
//    parameterBuilder {
//      addAnnotation(annotationSpec)
//    }
//  }

  override fun build(): KotlinConstructorPropertySpec {
    val parameter = parameterBuilder.build()
    val property = propertyBuilder
      .builder {
        initializer(parameter.name)
      }
      .build()

    return KotlinConstructorPropertySpec(parameter = parameter, property = property)
  }

  override fun spec(): KotlinConstructorPropertySpec = build()
}


typealias KotlinConstructorPropertySpecBuilderReceiver = KotlinConstructorPropertySpecBuilder.() -> Unit
