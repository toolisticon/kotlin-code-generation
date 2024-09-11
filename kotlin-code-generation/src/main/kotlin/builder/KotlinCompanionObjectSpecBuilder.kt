package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import io.toolisticon.kotlin.generation.poet.FunSpecSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.*
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Builder for [KotlinCompanionObjectSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinCompanionObjectSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinCompanionObjectSpecBuilder, KotlinCompanionObjectSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinCompanionObjectSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinCompanionObjectSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinCompanionObjectSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinCompanionObjectSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinCompanionObjectSpecBuilder> {

  companion object {
    /**
     * Creates new builder.
     */
    fun builder(name: String? = null): KotlinCompanionObjectSpecBuilder = KotlinCompanionObjectSpecBuilder(name)
  }

  internal constructor(name: String? = null) : this(TypeSpecBuilder.Companion.companionObjectBuilder(name))

  internal fun addOriginatingElement(originatingElement: Element) = builder {
    delegate.addOriginatingElement(
      originatingElement
    )
  }

  fun addTypeVariable(typeVariable: TypeVariableName) = builder { delegate.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?) = builder {
    delegate.primaryConstructor(
      primaryConstructor?.get()
    )
  }

  fun addInitializerBlock(block: CodeBlock) = builder { delegate.addInitializerBlock(block) }

  override fun build(): KotlinCompanionObjectSpec = KotlinCompanionObjectSpec(spec = delegate.build())

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun contextReceivers(vararg receiverTypes: TypeName) = builder { delegate.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier) = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { delegate.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier) = apply { delegate.addProperty(propertySpec.get()) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { delegate.addType(typeSpec.get()) }
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  // </overrides>
}

@ExperimentalKotlinPoetApi
typealias KotlinCompanionObjectSpecBuilderReceiver = KotlinCompanionObjectSpecBuilder.() -> Unit
