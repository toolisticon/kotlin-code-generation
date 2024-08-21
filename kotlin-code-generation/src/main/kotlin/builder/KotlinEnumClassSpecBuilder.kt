package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import javax.lang.model.element.Element
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class KotlinEnumClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinEnumClassSpecBuilder, KotlinEnumClassSpec>,
  KotlinDocumentableBuilder<KotlinEnumClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinEnumClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinEnumClassSpecBuilder> {

  companion object {
    fun builder(name: String): KotlinEnumClassSpecBuilder = builder(simpleClassName(name))
    fun builder(className: ClassName): KotlinEnumClassSpecBuilder = KotlinEnumClassSpecBuilder(className)
  }

  internal constructor(className: ClassName) : this(className, TypeSpecBuilder.enumBuilder(className)) {
    delegate.addModifiers(KModifier.ENUM)
  }

  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinEnumClassSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinEnumClassSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinEnumClassSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }


  fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }


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

  fun addEnumConstant(name: String, typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build()) = builder { this.addEnumConstant(name, typeSpec) }
  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  fun addEnumConstant(name: String) = apply {
    delegate.addEnumConstant(name)
  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(className, delegate.build())
}

@ExperimentalKotlinPoetApi
typealias KotlinEnumClassSpecBuilderReceiver = KotlinEnumClassSpecBuilder.() -> Unit
