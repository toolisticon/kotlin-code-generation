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
  KotlinAnnotatableBuilder<KotlinEnumClassSpecBuilder>,
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

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier): KotlinEnumClassSpecBuilder = apply { delegate.addAnnotation(spec.get()) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinEnumClassSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinEnumClassSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinEnumClassSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier): KotlinEnumClassSpecBuilder = builder { this.addType(typeSpec.get()) }

  fun addEnumConstant(name: String): KotlinEnumClassSpecBuilder = apply { delegate.addEnumConstant(name) }
  fun addEnumConstant(name: String, typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build()): KotlinEnumClassSpecBuilder = builder { this.addEnumConstant(name, typeSpec) }

  fun contextReceivers(vararg receiverTypes: TypeName): KotlinEnumClassSpecBuilder = builder { this.contextReceivers(*receiverTypes) }
  fun addOriginatingElement(originatingElement: Element): KotlinEnumClassSpecBuilder = builder { this.addOriginatingElement(originatingElement) }

  fun addModifiers(vararg modifiers: KModifier): KotlinEnumClassSpecBuilder = builder { this.addModifiers(*modifiers) }

  fun addTypeVariable(typeVariable: TypeVariableName): KotlinEnumClassSpecBuilder = builder { this.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?): KotlinEnumClassSpecBuilder = builder { this.primaryConstructor(primaryConstructor?.get()) }
  fun superclass(superclass: TypeName): KotlinEnumClassSpecBuilder = builder { this.superclass(superclass) }
  fun superclass(superclass: KClass<*>): KotlinEnumClassSpecBuilder = builder { this.superclass(superclass) }

  fun addSuperclassConstructorParameter(format: String, vararg args: Any): KotlinEnumClassSpecBuilder = builder { this.addSuperclassConstructorParameter(format, *args) }
  fun addSuperclassConstructorParameter(codeBlock: CodeBlock): KotlinEnumClassSpecBuilder = builder { this.addSuperclassConstructorParameter(codeBlock) }

  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>): KotlinEnumClassSpecBuilder = builder { this.addSuperinterfaces(superinterfaces) }
  fun addSuperinterface(superinterface: TypeName): KotlinEnumClassSpecBuilder = builder { this.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock): KotlinEnumClassSpecBuilder = builder { this.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>): KotlinEnumClassSpecBuilder = builder { this.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock): KotlinEnumClassSpecBuilder = builder { this.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String): KotlinEnumClassSpecBuilder = builder { this.addSuperinterface(superinterface, constructorParameterName) }
  fun addSuperinterface(superinterface: TypeName, constructorParameter: String): KotlinEnumClassSpecBuilder = builder { this.addSuperinterface(superinterface, constructorParameter) }

  fun addInitializerBlock(block: CodeBlock): KotlinEnumClassSpecBuilder = builder { this.addInitializerBlock(block) }

  override fun builder(block: TypeSpecBuilderReceiver): KotlinEnumClassSpecBuilder = apply { delegate.builder.block() }
  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(className, delegate.build())
}

@ExperimentalKotlinPoetApi
typealias KotlinEnumClassSpecBuilderReceiver = KotlinEnumClassSpecBuilder.() -> Unit
