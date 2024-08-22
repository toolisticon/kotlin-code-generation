@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinObjectSpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class KotlinObjectSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinObjectSpecBuilder, KotlinObjectSpec>,
  KotlinDocumentableBuilder<KotlinObjectSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinObjectSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinObjectSpecBuilder> {
  companion object {
    fun builder(name: String): KotlinObjectSpecBuilder = builder(simpleClassName(name))
    fun builder(className: ClassName): KotlinObjectSpecBuilder = KotlinObjectSpecBuilder(className = className)
  }

  internal constructor(className: ClassName) : this(className, TypeSpecBuilder.objectBuilder(className))

  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinObjectSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinObjectSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinObjectSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }
  fun contextReceivers(vararg receiverTypes: TypeName): KotlinObjectSpecBuilder = builder { this.contextReceivers(*receiverTypes) }


  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }

  fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }

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

  override fun builder(block: TypeSpecBuilderReceiver): KotlinObjectSpecBuilder = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinObjectSpec = KotlinObjectSpec(className = className, spec = delegate.build())
}

@ExperimentalKotlinPoetApi
typealias KotlinObjectSpecBuilderReceiver = KotlinObjectSpecBuilder.() -> Unit
