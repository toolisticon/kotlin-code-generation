@file:Suppress(SUPPRESS_UNUSED)
package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.jvm.jvmInline
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import javax.lang.model.element.Element
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class KotlinValueClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinValueClassSpecBuilder, KotlinValueClassSpec>,
  ConstructorPropertySupport<KotlinValueClassSpecBuilder>,
  KotlinDocumentableBuilder<KotlinValueClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinValueClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinValueClassSpecBuilder>{

  companion object {
    fun builder(name: String): KotlinValueClassSpecBuilder = builder(simpleClassName(name))
    fun builder(className: ClassName): KotlinValueClassSpecBuilder = KotlinValueClassSpecBuilder(className)
  }

  lateinit var constructorProperty: KotlinConstructorPropertySpecSupplier

  internal constructor(className: ClassName) : this(
    className = className,
    delegate = TypeSpecBuilder.classBuilder(className)
  ) {
    delegate.addModifiers(KModifier.VALUE)
    delegate.builder.jvmInline()
  }


  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier): KotlinValueClassSpecBuilder = apply { this.constructorProperty = spec }
  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinValueClassSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinValueClassSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinValueClassSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }


  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }
  fun contextReceivers(vararg receiverTypes: TypeName): KotlinValueClassSpecBuilder = builder { this.contextReceivers(*receiverTypes) }
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

