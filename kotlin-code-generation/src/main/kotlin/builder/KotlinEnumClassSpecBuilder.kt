@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
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

  internal constructor(className: ClassName) : this(className, TypeSpecBuilder.enumBuilder(className)) {
    delegate.addModifiers(KModifier.ENUM)
  }

  fun addEnumConstant(name: String): KotlinEnumClassSpecBuilder = apply { delegate.addEnumConstant(name) }
  fun addEnumConstant(name: String, typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build()): KotlinEnumClassSpecBuilder = builder { this.addEnumConstant(name, typeSpec) }

  internal fun addOriginatingElement(originatingElement: Element): KotlinEnumClassSpecBuilder = builder { this.addOriginatingElement(originatingElement) }

  fun addTypeVariable(typeVariable: TypeVariableName): KotlinEnumClassSpecBuilder = builder { this.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?): KotlinEnumClassSpecBuilder = builder { this.primaryConstructor(primaryConstructor?.get()) }
  fun addInitializerBlock(block: CodeBlock): KotlinEnumClassSpecBuilder = builder { this.addInitializerBlock(block) }

  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(className, delegate.build())

  // region [overrides]
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
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
