package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.parameterBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.propertyBuilder
import io.toolisticon.kotlin.generation.spec.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorProperty
import kotlin.reflect.KClass

class KotlinConstructorPropertyBuilder internal constructor(
  override val name: String,
  private val type: TypeName,
  private val propertyBuilder: KotlinPropertyBuilder,
  private val parameterBuilder: KotlinParameterBuilder
) : Builder<KotlinConstructorProperty>, ConstructorPropertySupplier, KotlinAnnotatableBuilder<KotlinConstructorPropertyBuilder> {

  @Suppress(CLASS_NAME)
  object builder {

    operator fun invoke(name: String, type: KClass<*>): KotlinConstructorPropertyBuilder = invoke(
      name = name,
      type = type.asClassName(),
    )

    operator fun invoke(name: String, type: TypeName): KotlinConstructorPropertyBuilder {

      return KotlinConstructorPropertyBuilder(
        name = name,
        type = type,
        propertyBuilder = propertyBuilder(name, type),
        parameterBuilder = parameterBuilder(name, type)
      )
    }
  }

  fun makePrivate() = apply {
    propertyBuilder.makePrivate()
  }

  override fun addAnnotation(annotationSpec: AnnotationSpec): KotlinConstructorPropertyBuilder = apply {
    parameterBuilder.addAnnotation(annotationSpec)
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
