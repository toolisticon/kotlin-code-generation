@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.jvm.jvmInline
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.builder.KotlinFunSpecBuilder.Companion.constructorBuilder
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Builder for [KotlinValueClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinValueClassSpecBuilder internal constructor(
  val className: ClassName,
  internal val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinValueClassSpecBuilder, KotlinValueClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinValueClassSpecBuilder>,
  KotlinConstructorPropertySupport<KotlinValueClassSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinValueClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinValueClassSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinValueClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinValueClassSpecBuilder> {

  companion object {

    /**
     * Creates new builder.
     */
    fun builder(name: String): KotlinValueClassSpecBuilder = builder(simpleClassName(name))

    /**
     * Creates new builder.
     */
    fun builder(className: ClassName): KotlinValueClassSpecBuilder = KotlinValueClassSpecBuilder(className)
  }

  internal lateinit var constructorProperty: KotlinConstructorPropertySpecSupplier

  internal constructor(className: ClassName) : this(className = className, delegate = TypeSpecBuilder.classBuilder(className)) {
    delegate.addModifiers(KModifier.VALUE)
    delegate.builder.jvmInline()
  }

  internal fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }
  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }

  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  override fun build(): KotlinValueClassSpec = build { }

  /**
   * Use internally when creating extra builders that re-use this builder.
   */
  internal fun build(block: KotlinValueClassSpecBuilderReceiver): KotlinValueClassSpec {
    check(::constructorProperty.isInitialized) { "Value class must have exactly one property." }

    val constructor = constructorBuilder()
      .addParameter(this.constructorProperty.spec().parameter)
      .build()

    delegate.addProperty(constructorProperty.spec().property.get())
    delegate.builder.primaryConstructor(constructor.get())

    this.block()

    return KotlinValueClassSpec(className = className, spec = delegate.build())
  }

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply { this.constructorProperty = spec }
  override fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier) = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier) = apply { delegate.addProperty(propertySpec.get()) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  // </overrides>
}

@ExperimentalKotlinPoetApi
typealias KotlinValueClassSpecBuilderReceiver = KotlinValueClassSpecBuilder.() -> Unit

