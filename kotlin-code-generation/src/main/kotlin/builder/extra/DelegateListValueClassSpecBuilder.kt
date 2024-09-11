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
 * Generator that wraps a list holding given elementType in a value class and delegates the list.
 */
@ExperimentalKotlinPoetApi
class DelegateListValueClassSpecBuilder internal constructor(
  private val delegate: KotlinValueClassSpecBuilder,
  private val elementType: TypeName,
) : KotlinGeneratorTypeSpecBuilder<DelegateListValueClassSpecBuilder, KotlinValueClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<DelegateListValueClassSpecBuilder>,
  KotlinSuperInterfaceSupport<DelegateListValueClassSpecBuilder> {

  companion object {
    /**
     * Creates new builder.
     */
    fun builder(name: SimpleName, elementType: TypeName) = builder(
      className = simpleClassName(name),
      elementType = elementType
    )

    /**
     * Creates new builder.
     */
    fun builder(className: ClassName, elementType: TypeName) = DelegateListValueClassSpecBuilder(
      className = className,
      elementType = elementType
    )
  }

  private var propertyName: String = "delegate"

  internal constructor(className: ClassName, elementType: TypeName) : this(
    delegate = KotlinValueClassSpecBuilder(className),
    elementType = List::class.asClassName().parameterizedBy(elementType),
  ) {
    delegate.addTag(ClassSpecType.LIST)
  }

  /**
   * Modify the default property name.
   */
  fun propertyName(propertyName: String) = apply {
    require(propertyName.isNotBlank()) { "Property name cannot be blank." }
    this.propertyName = propertyName
  }

  override fun build(): KotlinValueClassSpec {
    delegate.addConstructorProperty(propertyName, elementType) {
      makePrivate()
    }
    val constructorProperty = delegate.constructorProperty
    return delegate.build {
      addSuperinterface(elementType, constructorProperty.name)
    }
  }

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc) }
  override fun addModifiers(vararg modifiers: KModifier) = apply { delegate.addModifiers(*modifiers) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = apply { delegate.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = apply { this.delegate.addSuperinterface(superinterface, delegate) }
  override fun addTag(type: KClass<*>, tag: Any?) = apply { delegate.addTag(type, tag) }
  override fun builder(block: TypeSpec.Builder.() -> Unit) = apply { delegate.builder(block) }
  // </overrides>
}

@ExperimentalKotlinPoetApi
typealias DelegateListValueClassSpecBuilderReceiver = DelegateListValueClassSpecBuilder.() -> Unit
