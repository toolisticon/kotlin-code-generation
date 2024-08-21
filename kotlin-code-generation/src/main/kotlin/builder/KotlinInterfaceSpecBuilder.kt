package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinInterfaceSpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import javax.lang.model.element.Element
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class KotlinInterfaceSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinInterfaceSpecBuilder, KotlinInterfaceSpec>,
  KotlinDocumentableBuilder<KotlinInterfaceSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinInterfaceSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinInterfaceSpecBuilder> {
  companion object {
    private const val DEFAULT_IS_FUNCTIONAL = false
    fun builder(name: String, isFunctionInterface: Boolean = DEFAULT_IS_FUNCTIONAL): KotlinInterfaceSpecBuilder = builder(simpleClassName(name), isFunctionInterface)
    fun builder(className: ClassName, isFunctionInterface: Boolean = DEFAULT_IS_FUNCTIONAL): KotlinInterfaceSpecBuilder = KotlinInterfaceSpecBuilder(className, isFunctionInterface)
  }

  internal constructor(className: ClassName, funInterface: Boolean = false) : this(
    className = className,
    delegate = if (funInterface) TypeSpecBuilder.funInterfaceBuilder(className) else TypeSpecBuilder.interfaceBuilder(className)
  )

  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinInterfaceSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinInterfaceSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinInterfaceSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }
  fun contextReceivers(vararg receiverTypes: TypeName): KotlinInterfaceSpecBuilder = builder { this.contextReceivers(*receiverTypes) }
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


  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinInterfaceSpec = KotlinInterfaceSpec(className = className, spec = delegate.build())
}

@ExperimentalKotlinPoetApi
typealias KotlinInterfaceSpecBuilderReceiver = KotlinInterfaceSpecBuilder.() -> Unit
