package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.*
import kotlin.reflect.KClass

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
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinConstructorPropertySpecBuilder>,
  KotlinConstructorPropertySpecSupplier {

  companion object {
    fun builder(name: String, type: TypeName): KotlinConstructorPropertySpecBuilder = KotlinConstructorPropertySpecBuilder(
      name = name,
      type = type,
      propertyBuilder = KotlinPropertySpecBuilder.builder(name = name, type = type),
      parameterBuilder = KotlinParameterSpecBuilder.builder(name = name, type = type)
    )

    internal fun TypeSpecBuilder.primaryConstructorWithProperties(constructorProperties: List<KotlinConstructorPropertySpec>): FunSpecBuilder {
      val constructor = FunSpec.constructorBuilder().wrap()
      constructorProperties.forEach {
        constructor.addParameter(it.parameter.get())
        this.addProperty(it.property.get())
      }

      return constructor
    }
  }

  override fun build(): KotlinConstructorPropertySpec {
    val parameter = parameterBuilder.build()
    val property = propertyBuilder
      .builder {
        initializer(parameter.name)
      }
      .build()

    return KotlinConstructorPropertySpec(parameter = parameter, property = property)
  }

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { parameterBuilder.addAnnotation(spec) }
  override fun addKdoc(kdoc: KDoc) = apply { parameterBuilder.addKdoc(kdoc) }
  override fun addModifiers(vararg modifiers: KModifier) = apply { propertyBuilder.addModifiers(*modifiers) }
  override fun addTag(type: KClass<*>, tag: Any?) = apply { propertyBuilder.addTag(type, tag) }
  override fun spec(): KotlinConstructorPropertySpec = build()
  // </overrides>

}

@ExperimentalKotlinPoetApi
typealias KotlinConstructorPropertySpecBuilderReceiver = KotlinConstructorPropertySpecBuilder.() -> Unit
