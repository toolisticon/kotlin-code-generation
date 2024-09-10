@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Builder for [KotlinClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinClassSpecBuilder internal constructor(
  internal val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinClassSpecBuilder, KotlinClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinClassSpecBuilder>,
  KotlinConstructorPropertySupport<KotlinClassSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinClassSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinClassSpecBuilder> {

  companion object {
    fun builder(name: String): KotlinClassSpecBuilder = builder(simpleClassName(name))
    fun builder(className: ClassName): KotlinClassSpecBuilder = KotlinClassSpecBuilder(className = className)
  }

  internal constructor(className: ClassName) : this(className, TypeSpecBuilder.classBuilder(className))

  private val constructorProperties: LinkedHashMap<String, KotlinConstructorPropertySpecSupplier> = LinkedHashMap()
  private var isSetPrimaryConstructor: Boolean = false

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply { constructorProperties[spec.name] = spec }
  override fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier) = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier) = apply { delegate.addProperty(propertySpec.get()) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }
  override fun tag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }
  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }

  fun primaryConstructor(primaryConstructor: FunSpecSupplier?) = apply {
    if (primaryConstructor != null) {
      delegate.primaryConstructor(primaryConstructor.get())
      isSetPrimaryConstructor = true
    }
  }

  fun superclass(superclass: TypeName) = builder { this.superclass(superclass) }
  fun superclass(superclass: KClass<*>) = builder { this.superclass(superclass) }

  fun addSuperclassConstructorParameter(format: String, vararg args: Any) = builder { this.addSuperclassConstructorParameter(format, *args) }
  fun addSuperclassConstructorParameter(codeBlock: CodeBlock) = builder { this.addSuperclassConstructorParameter(codeBlock) }


  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  override fun build(): KotlinClassSpec {
    val hasConstructorProperties = constructorProperties.isNotEmpty()
    check(!(hasConstructorProperties && isSetPrimaryConstructor)) { "Decide if you want to use the constructorProperty support OR define a custom primary constructor, not both." }

    if (hasConstructorProperties) {
      delegate.primaryConstructorWithProperties(toList(constructorProperties.values))
    }

    return KotlinClassSpec(className = className, spec = delegate.build())
  }
}

@ExperimentalKotlinPoetApi
typealias KotlinClassSpecBuilderReceiver = KotlinClassSpecBuilder.() -> Unit
