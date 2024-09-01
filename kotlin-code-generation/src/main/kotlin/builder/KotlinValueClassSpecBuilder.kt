@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.jvm.jvmInline
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element

/**
 * Builder for [KotlinValueClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinValueClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinValueClassSpecBuilder, KotlinValueClassSpec>,
  KotlinConstructorPropertySupport<KotlinValueClassSpecBuilder>,
  KotlinAnnotatableBuilder<KotlinValueClassSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinValueClassSpecBuilder>,
  KotlinDocumentableBuilder<KotlinValueClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinValueClassSpecBuilder>,
  KotlinModifiableBuilder<KotlinValueClassSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinValueClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinValueClassSpecBuilder> {

  companion object {
    fun builder(name: String): KotlinValueClassSpecBuilder = builder(simpleClassName(name))
    fun builder(className: ClassName): KotlinValueClassSpecBuilder = KotlinValueClassSpecBuilder(className)
  }

  lateinit var constructorProperty: KotlinConstructorPropertySpecSupplier

  internal constructor(className: ClassName) : this(className = className, delegate = TypeSpecBuilder.classBuilder(className)) {
    delegate.addModifiers(KModifier.VALUE)
    delegate.builder.jvmInline()
  }

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier): KotlinValueClassSpecBuilder = apply { delegate.addAnnotation(spec.get()) }
  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier): KotlinValueClassSpecBuilder = apply { this.constructorProperty = spec }
  override fun contextReceivers(vararg receiverTypes: TypeName): KotlinValueClassSpecBuilder = builder { this.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinValueClassSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinValueClassSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinValueClassSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }
  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }

  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }

  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinValueClassSpec {
    check(::constructorProperty.isInitialized) { "Value class must have exactly one property." }

    val constructor = KotlinFunSpecBuilder.constructorBuilder()
      .addParameter(this.constructorProperty.spec().parameter)
      .build()

    delegate.addProperty(constructorProperty.spec().property.get())
    delegate.builder.primaryConstructor(constructor.get())

    return KotlinValueClassSpec(className = className, spec = delegate.build())
  }
}

@ExperimentalKotlinPoetApi
typealias KotlinValueClassSpecBuilderReceiver = KotlinValueClassSpecBuilder.() -> Unit

