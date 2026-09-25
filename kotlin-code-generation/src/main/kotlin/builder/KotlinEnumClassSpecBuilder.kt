@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.simpleClassName
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.toList
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Builder for [KotlinEnumClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinEnumClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinEnumClassSpecBuilder, KotlinEnumClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinEnumClassSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinEnumClassSpecBuilder>,
  KotlinConstructorPropertySupport<KotlinEnumClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinEnumClassSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinEnumClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinEnumClassSpecBuilder> {

  companion object {
    /**
     * Creates new builder.
     */
    fun builder(name: String): KotlinEnumClassSpecBuilder = builder(simpleClassName(name))

    /**
     * Creates new builder.
     */
    fun builder(className: ClassName): KotlinEnumClassSpecBuilder = KotlinEnumClassSpecBuilder(className)
  }

  private val constructorProperties = LinkedHashMap<String, KotlinConstructorPropertySpecSupplier>()
  private var isSetPrimaryConstructor: Boolean = false

  internal constructor(className: ClassName) : this(className, TypeSpecBuilder.enumBuilder(className)) {
    delegate.addModifiers(KModifier.ENUM)
  }

  fun addEnumConstant(name: String): KotlinEnumClassSpecBuilder = apply { delegate.addEnumConstant(name) }
  fun addEnumConstant(name: String, typeSpec: TypeSpec): KotlinEnumClassSpecBuilder = builder { this.addEnumConstant(name, typeSpec) }
  fun addEnumConstant(name: String, spec: KotlinAnonymousClassSpecSupplier): KotlinEnumClassSpecBuilder = apply { delegate.addEnumConstant(name, spec.get()) }
  fun addEnumConstant(name: String, block: KotlinAnonymousClassSpecBuilderReceiver): KotlinEnumClassSpecBuilder = addEnumConstant(name, KotlinAnonymousClassSpecBuilder.builder().also(block))
  fun addEnumConstant(name: String, format: String, vararg args: Any): KotlinEnumClassSpecBuilder = addEnumConstant(
    name = name,
    spec = KotlinAnonymousClassSpecBuilder.builder().apply {
      addSuperclassConstructorParameter(format, *args)
    }
  )
  fun addEnumConstant(name: String, codeBlock: CodeBlock): KotlinEnumClassSpecBuilder = addEnumConstant(
    name = name,
    spec = KotlinAnonymousClassSpecBuilder.builder().apply {
      addSuperclassConstructorParameter(codeBlock)
    }
  )

  internal fun addOriginatingElement(originatingElement: Element): KotlinEnumClassSpecBuilder = builder { this.addOriginatingElement(originatingElement) }

  fun addTypeVariable(typeVariable: TypeVariableName): KotlinEnumClassSpecBuilder = builder { this.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?): KotlinEnumClassSpecBuilder = apply {
    if (primaryConstructor != null) {
      delegate.primaryConstructor(primaryConstructor.get())
      isSetPrimaryConstructor = true
    }
  }
  fun addInitializerBlock(block: CodeBlock): KotlinEnumClassSpecBuilder = builder { this.addInitializerBlock(block) }

  override fun build(): KotlinEnumClassSpec {
    val hasConstructorProperties = constructorProperties.isNotEmpty()
    check(!(hasConstructorProperties && isSetPrimaryConstructor)) { "Decide if you want to use the constructorProperty support OR define a custom primary constructor, not both." }

    if (hasConstructorProperties) {
      val constructor = delegate.primaryConstructorWithProperties(toList(constructorProperties.values))
      delegate.primaryConstructor(constructor.build())
    }

    return KotlinEnumClassSpec(className, delegate.build())
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
  override fun builder(block: TypeSpecBuilderReceiver): KotlinEnumClassSpecBuilder = apply { delegate.builder.block() }
  // endregion [overrides]
}

@ExperimentalKotlinPoetApi
typealias KotlinEnumClassSpecBuilderReceiver = KotlinEnumClassSpecBuilder.() -> Unit
