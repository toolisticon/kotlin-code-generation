package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.jvm.jvmInline
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpecSupplier
import javax.lang.model.element.Element
import kotlin.reflect.KClass

class KotlinValueClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinValueClassSpec, TypeSpec>,
  KotlinValueClassSpecSupplier,
  ConstructorPropertySupport<KotlinValueClassSpecBuilder>,
  DelegatingBuilder<KotlinValueClassSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {

    @JvmStatic
    fun builder(name: String): KotlinValueClassSpecBuilder = KotlinValueClassSpecBuilder(
      className = ClassName("", name),
      delegate = TypeSpecBuilder.classBuilder(name)
    )

    @JvmStatic
    fun builder(className: ClassName): KotlinValueClassSpecBuilder = KotlinValueClassSpecBuilder(className)
  }

  lateinit var constructorProperty: KotlinConstructorPropertySpecSupplier

  internal constructor(className: ClassName) : this(
    className = className,
    delegate = TypeSpecBuilder.classBuilder(className)
  )

  init {
    delegate.addModifiers(KModifier.VALUE)
    delegate.builder.jvmInline()
  }

  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier): KotlinValueClassSpecBuilder = apply {
    this.constructorProperty = spec
  }


  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }

  fun addKdoc(format: String, vararg args: Any) = builder { addKdoc(format, *args) }
  fun addKdoc(block: CodeBlock) = builder { addKdoc(block) }

  fun contextReceivers(vararg receiverTypes: TypeName)= builder { this.contextReceivers(*receiverTypes) }

  fun addFunction(funSpec: FunSpecSupplier)= builder { this.addFunction(funSpec.get()) }
  fun addProperty(propertySpec: PropertySpecSupplier)= builder { this.addProperty(propertySpec.get()) }

  fun addOriginatingElement(originatingElement: Element)= builder { this.addOriginatingElement(originatingElement) }
  fun addType(typeSpec: TypeSpecSupplier)= builder { this.addType(typeSpec.get()) }

  fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }

  fun addTypeVariable(typeVariable: TypeVariableName)= builder { this.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpecSupplier?)= builder { this.primaryConstructor(primaryConstructor?.get()) }
  fun superclass(superclass: TypeName)= builder { this.superclass(superclass) }
  fun superclass(superclass: KClass<*>)= builder { this.superclass(superclass) }

  fun addSuperclassConstructorParameter(format: String, vararg args: Any)= builder { this.addSuperclassConstructorParameter(format, *args) }
  fun addSuperclassConstructorParameter(codeBlock: CodeBlock)= builder { this.addSuperclassConstructorParameter(codeBlock) }

  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>)= builder { this.addSuperinterfaces(superinterfaces) }
  fun addSuperinterface(superinterface: TypeName)= builder { this.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock)= builder { this.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>)= builder { this.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock)= builder { this.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String)= builder { this.addSuperinterface(superinterface, constructorParameterName) }
  fun addSuperinterface(superinterface: TypeName, constructorParameter: String)= builder { this.addSuperinterface(superinterface, constructorParameter) }

  fun addEnumConstant(name: String, typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build())= builder { this.addEnumConstant(name, typeSpec) }
  fun addInitializerBlock(block: CodeBlock)= builder { this.addInitializerBlock(block) }


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

  override fun spec(): KotlinValueClassSpec = build()
  override fun get(): TypeSpec = build().get()
}

typealias KotlinValueClassSpecBuilderReceiver = KotlinValueClassSpecBuilder.() -> Unit

