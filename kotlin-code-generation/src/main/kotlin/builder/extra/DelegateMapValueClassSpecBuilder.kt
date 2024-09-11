package io.toolisticon.kotlin.generation.builder.extra

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.SimpleName
import io.toolisticon.kotlin.generation.builder.KotlinAnnotatableDocumentableModifiableBuilder
import io.toolisticon.kotlin.generation.builder.KotlinGeneratorTypeSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinSuperInterfaceSupport
import io.toolisticon.kotlin.generation.builder.KotlinValueClassSpecBuilder
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.spec.ClassSpecType
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import kotlin.reflect.KClass

/**
 * Generator that wraps a map holding given key/valueType in a value class and delegates the map.
 */
@ExperimentalKotlinPoetApi
class DelegateMapValueClassSpecBuilder internal constructor(
  private val delegate: KotlinValueClassSpecBuilder,
  private val mapType: TypeName,
) : KotlinGeneratorTypeSpecBuilder<DelegateMapValueClassSpecBuilder, KotlinValueClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<DelegateMapValueClassSpecBuilder>,
  KotlinSuperInterfaceSupport<DelegateMapValueClassSpecBuilder> {

  companion object {
    val DEFAULT_KEY_TYPE = String::class.asTypeName()

    /**
     * Creates new builder.
     */
    fun builder(name: SimpleName, keyType: TypeName = DEFAULT_KEY_TYPE, valueType: TypeName) = builder(className = simpleClassName(name), keyType = keyType, valueType = valueType)

    /**
     * Creates new builder.
     */
    fun builder(className: ClassName, keyType: TypeName = DEFAULT_KEY_TYPE, valueType: TypeName) = DelegateMapValueClassSpecBuilder(
      className = className,
      keyType = keyType,
      valueType = valueType
    )
  }

  private var propertyName: String = "delegate"

  internal constructor(className: ClassName, keyType: TypeName, valueType: TypeName) : this(
    delegate = KotlinValueClassSpecBuilder(className),
    mapType = Map::class.asTypeName().parameterizedBy(keyType, valueType)
  ) {
    delegate.addTag(ClassSpecType.MAP)
  }

  /**
   * Modify the default property name.
   */
  fun propertyName(propertyName: String) = apply {
    require(propertyName.isNotBlank()) { "Property name cannot be blank." }
    this.propertyName = propertyName
  }

  override fun build(): KotlinValueClassSpec {
    delegate.addConstructorProperty(propertyName, mapType) {
      makePrivate()
    }
    val constructorProperty = delegate.constructorProperty
    return delegate.build {
      addSuperinterface(mapType, constructorProperty.name)
    }
  }

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc) }
  override fun addModifiers(vararg modifiers: KModifier) = apply { delegate.addModifiers(*modifiers) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = apply { delegate.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = apply { this.delegate.addSuperinterface(superinterface, delegate) }
  override fun builder(block: TypeSpec.Builder.() -> Unit) = apply { delegate.builder(block) }
  override fun addTag(type: KClass<*>, tag: Any?) = apply { delegate.addTag(type, tag) }
  // </overrides>
}

@ExperimentalKotlinPoetApi
typealias DelegateMapValueClassSpecBuilderReceiver = DelegateMapValueClassSpecBuilder.() -> Unit
