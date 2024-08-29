@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnonymousClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Builder for [KotlinAnonymousClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinAnonymousClassSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinAnonymousClassSpecBuilder, KotlinAnonymousClassSpec>,
  KotlinAnnotatableBuilder<KotlinAnonymousClassSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinAnonymousClassSpecBuilder>,
  KotlinDocumentableBuilder<KotlinAnonymousClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinAnonymousClassSpecBuilder>,
  KotlinModifiableBuilder<KotlinAnonymousClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinAnonymousClassSpecBuilder>,
  DelegatingBuilder<KotlinAnonymousClassSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {
    fun builder(): KotlinAnonymousClassSpecBuilder = KotlinAnonymousClassSpecBuilder()
  }

  internal constructor() : this(delegate = TypeSpecBuilder.anonymousClassBuilder())

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier): KotlinAnonymousClassSpecBuilder = apply { delegate.addAnnotation(spec.get()) }
  override fun contextReceivers(vararg receiverTypes: TypeName): KotlinAnonymousClassSpecBuilder = builder { this.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinAnonymousClassSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinAnonymousClassSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinAnonymousClassSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }

  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?) = builder { this.primaryConstructor(primaryConstructor?.get()) }
  fun superclass(superclass: TypeName) = builder { this.superclass(superclass) }
  fun superclass(superclass: KClass<*>) = builder { this.superclass(superclass) }

  fun addSuperclassConstructorParameter(format: String, vararg args: Any) = builder { this.addSuperclassConstructorParameter(format, *args) }
  fun addSuperclassConstructorParameter(codeBlock: CodeBlock) = builder { this.addSuperclassConstructorParameter(codeBlock) }

  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>) = builder { this.addSuperinterfaces(superinterfaces) }
  fun addSuperinterface(superinterface: TypeName) = builder { this.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>) = builder { this.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String) = builder { this.addSuperinterface(superinterface, constructorParameterName) }
  fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }

  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  override fun build(): KotlinAnonymousClassSpec = KotlinAnonymousClassSpec(delegate.build())
}

@ExperimentalKotlinPoetApi
typealias KotlinAnonymousClassSpecBuilderReceiver = KotlinAnonymousClassSpecBuilder.() -> Unit
