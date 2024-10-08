@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import mu.KLogging
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Builder for [KotlinDataClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinDataClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinDataClassSpecBuilder, KotlinDataClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinDataClassSpecBuilder>,
  KotlinConstructorPropertySupport<KotlinDataClassSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinDataClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinDataClassSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinDataClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinDataClassSpecBuilder> {
  companion object : KLogging() {
    /**
     * Creates new builder.
     */
    fun builder(name: String): KotlinDataClassSpecBuilder = builder(simpleClassName(name))

    /**
     * Creates new builder.
     */
    fun builder(className: ClassName): KotlinDataClassSpecBuilder = KotlinDataClassSpecBuilder(className)
  }

  internal constructor (className: ClassName) : this(
    className = className,
    delegate = TypeSpecBuilder.classBuilder(className)
  )

  init {
    delegate.addModifiers(KModifier.DATA)
  }

  private val constructorProperties = LinkedHashMap<String, KotlinConstructorPropertySpecSupplier>()

  internal fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }

  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?) = builder { this.primaryConstructor(primaryConstructor?.get()) }

  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  override fun build(): KotlinDataClassSpec {
    check(constructorProperties.isNotEmpty()) { "Data class must have at least one property." }

    val constructor = delegate.primaryConstructorWithProperties(toList(constructorProperties.values))
    delegate.primaryConstructor(constructor.build())

    return KotlinDataClassSpec(className = className, spec = delegate.build())
  }

  // region [overrides]
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply { this.constructorProperties[spec.name] = spec }
  override fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier) = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier) = apply { delegate.addProperty(propertySpec.get()) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  // endregion [overrides]
}

@ExperimentalKotlinPoetApi
typealias KotlinDataClassSpecBuilderReceiver = KotlinDataClassSpecBuilder.() -> Unit
