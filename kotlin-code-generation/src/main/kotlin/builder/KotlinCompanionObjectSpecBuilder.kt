package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.poet.FunSpecSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinCompanionObjectSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import javax.lang.model.element.Element
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class KotlinCompanionObjectSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinCompanionObjectSpecBuilder, KotlinCompanionObjectSpec>,
  KotlinDocumentableBuilder<KotlinCompanionObjectSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinCompanionObjectSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinCompanionObjectSpecBuilder> {

  companion object {
    fun builder(name: String? = null): KotlinCompanionObjectSpecBuilder = KotlinCompanionObjectSpecBuilder(name)
  }

  internal constructor(name: String? = null) : this(TypeSpecBuilder.Companion.companionObjectBuilder(name))

  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinCompanionObjectSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinCompanionObjectSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinCompanionObjectSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { delegate.addType(typeSpec.get()) }

  fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = builder {
    delegate.addAnnotation(spec.get())
  }

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { delegate.addAnnotation(annotationSpec.get()) }

  fun contextReceivers(vararg receiverTypes: TypeName): KotlinCompanionObjectSpecBuilder = builder {
    delegate.contextReceivers(
      *receiverTypes
    )
  }

  fun addOriginatingElement(originatingElement: Element) = builder {
    delegate.addOriginatingElement(
      originatingElement
    )
  }

  fun addModifiers(vararg modifiers: KModifier) = builder { delegate.addModifiers(*modifiers) }

  fun addTypeVariable(typeVariable: TypeVariableName) = builder { delegate.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?) = builder {
    delegate.primaryConstructor(
      primaryConstructor?.get()
    )
  }

  fun superclass(superclass: TypeName) = builder { delegate.superclass(superclass) }
  fun superclass(superclass: KClass<*>) = builder { delegate.superclass(superclass) }

  fun addSuperclassConstructorParameter(format: String, vararg args: Any) = builder {
    delegate.addSuperclassConstructorParameter(
      format,
      *args
    )
  }

  fun addSuperclassConstructorParameter(codeBlock: CodeBlock) = builder {
    delegate.addSuperclassConstructorParameter(
      codeBlock
    )
  }

  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>) = builder {
    delegate.addSuperinterfaces(
      superinterfaces
    )
  }

  fun addSuperinterface(superinterface: TypeName) = builder { delegate.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: TypeName, codeBlock: CodeBlock) = builder {
    delegate.addSuperinterface(
      superinterface,
      codeBlock
    )
  }

  fun addSuperinterface(superinterface: KClass<*>) = builder { delegate.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: KClass<*>, codeBlock: CodeBlock) = builder {
    delegate.addSuperinterface(
      superinterface,
      codeBlock
    )
  }

  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String) = builder {
    delegate.addSuperinterface(
      superinterface,
      constructorParameterName
    )
  }

  fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder {
    delegate.addSuperinterface(
      superinterface,
      constructorParameter
    )
  }

  fun addEnumConstant(name: String, typeSpec: TypeSpec = TypeSpec.Companion.anonymousClassBuilder().build()) = builder {
    delegate.addEnumConstant(
      name,
      typeSpec
    )
  }

  fun addInitializerBlock(block: CodeBlock) = builder { delegate.addInitializerBlock(block) }


  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  override fun build(): KotlinCompanionObjectSpec = KotlinCompanionObjectSpec(spec = delegate.build())
}


@ExperimentalKotlinPoetApi
typealias KotlinCompanionObjectSpecBuilderReceiver = KotlinCompanionObjectSpecBuilder.() -> Unit
