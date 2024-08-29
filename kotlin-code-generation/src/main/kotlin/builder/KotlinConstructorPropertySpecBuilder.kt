package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpec
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier

/**
 * Builder for [KotlinConstructorPropertySpec].
 */
@ExperimentalKotlinPoetApi
class KotlinConstructorPropertySpecBuilder internal constructor(
  override val name: String,
  private val type: TypeName,
  private val propertyBuilder: KotlinPropertySpecBuilder,
  private val parameterBuilder: KotlinParameterSpecBuilder
) : Builder<KotlinConstructorPropertySpec>,
  KotlinAnnotatableBuilder<KotlinConstructorPropertySpecBuilder>,
  KotlinConstructorPropertySpecSupplier,
  KotlinDocumentableBuilder<KotlinConstructorPropertySpecBuilder>,
  KotlinModifiableBuilder<KotlinConstructorPropertySpecBuilder> {

  companion object {
    fun builder(name: String, type: TypeName): KotlinConstructorPropertySpecBuilder = KotlinConstructorPropertySpecBuilder(
      name = name,
      type = type,
      propertyBuilder = KotlinPropertySpecBuilder.builder(name = name, type = type),
      parameterBuilder = KotlinParameterSpecBuilder.builder(name = name, type = type)
    )

    internal fun TypeSpecBuilder.primaryConstructorWithProperties(constructorProperties: List<KotlinConstructorPropertySpec>) {
      val constructor = FunSpec.constructorBuilder()
      constructorProperties.forEach {
        constructor.addParameter(it.parameter.get())
        this.addProperty(it.property.get())
      }

      this.primaryConstructor(constructor.build())
    }
  }

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier): KotlinConstructorPropertySpecBuilder = apply { parameterBuilder.addAnnotation(spec) }
  override fun addKdoc(kdoc: KDoc): KotlinConstructorPropertySpecBuilder = apply { parameterBuilder.addKdoc(kdoc) }
  override fun addModifiers(vararg modifiers: KModifier): KotlinConstructorPropertySpecBuilder = apply{ propertyBuilder.addModifiers(*modifiers) }

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

@ExperimentalKotlinPoetApi
typealias KotlinConstructorPropertySpecBuilderReceiver = KotlinConstructorPropertySpecBuilder.() -> Unit
