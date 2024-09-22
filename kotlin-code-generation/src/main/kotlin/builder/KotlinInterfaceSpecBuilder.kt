@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinInterfaceSpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Builder for [KotlinInterfaceSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinInterfaceSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinInterfaceSpecBuilder, KotlinInterfaceSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinInterfaceSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinInterfaceSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinInterfaceSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinInterfaceSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinInterfaceSpecBuilder> {
  companion object {
    private const val DEFAULT_IS_FUNCTIONAL = false

    /**
     * Creates new builder.
     */
    fun builder(name: String, isFunctionInterface: Boolean = DEFAULT_IS_FUNCTIONAL): KotlinInterfaceSpecBuilder = builder(simpleClassName(name), isFunctionInterface)

    /**
     * Creates new builder.
     */
    fun builder(className: ClassName, isFunctionInterface: Boolean = DEFAULT_IS_FUNCTIONAL): KotlinInterfaceSpecBuilder = KotlinInterfaceSpecBuilder(className, isFunctionInterface)
  }

  internal constructor(className: ClassName, funInterface: Boolean = false) : this(
    className = className,
    delegate = if (funInterface) TypeSpecBuilder.funInterfaceBuilder(className) else TypeSpecBuilder.interfaceBuilder(className)
  )

  internal fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }
  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }

  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  override fun build(): KotlinInterfaceSpec = KotlinInterfaceSpec(className = className, spec = delegate.build())

  // region [overrides]
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier) = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier) = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  // endregion [overrides]
}

@ExperimentalKotlinPoetApi
typealias KotlinInterfaceSpecBuilderReceiver = KotlinInterfaceSpecBuilder.() -> Unit
