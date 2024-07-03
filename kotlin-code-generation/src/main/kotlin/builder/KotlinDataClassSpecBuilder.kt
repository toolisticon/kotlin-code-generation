package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.*
import mu.KLogging
import javax.lang.model.element.Element
import kotlin.reflect.KClass

class KotlinDataClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinDataClassSpec, TypeSpec>,
  KotlinDataClassSpecSupplier,
  ConstructorPropertySupport<KotlinDataClassSpecBuilder>,
  DelegatingBuilder<KotlinDataClassSpecBuilder, TypeSpecBuilderReceiver> {
  companion object : KLogging() {

    @JvmStatic
    fun builder(name: String): KotlinDataClassSpecBuilder = KotlinDataClassSpecBuilder(
      className = ClassName("", name),
      delegate = TypeSpecBuilder.classBuilder(name)
    )

    @JvmStatic
    fun builder(className: ClassName): KotlinDataClassSpecBuilder = KotlinDataClassSpecBuilder(className)
  }

  internal constructor(className: ClassName) : this(
    className = className,
    delegate = TypeSpecBuilder.classBuilder(className)
  )

  init {
    delegate.addModifiers(KModifier.DATA)
  }

  private val constructorProperties = LinkedHashMap<String, KotlinConstructorPropertySpecSupplier>()

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinDataClassSpec {
    check(constructorProperties.isNotEmpty()) { "Data class must have at least one property." }

    delegate.primaryConstructorWithProperties(toList(constructorProperties.values))

    return KotlinDataClassSpec(className = className, spec = delegate.build())
  }


  override fun spec(): KotlinDataClassSpec = build()
  override fun get(): TypeSpec = build().get()

  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply {
    this.constructorProperties[spec.name] = spec
  }

  fun addAnnotation(annotation: KotlinAnnotationSpecSupplier) = apply {
    delegate.addAnnotation(annotation.get())
  }

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }

  fun addKdoc(format: String, vararg args: Any) = builder { addKdoc(format, *args) }
  fun addKdoc(block: CodeBlock) = builder { addKdoc(block) }

  fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }

  fun addFunction(funSpec: FunSpecSupplier) = builder { this.addFunction(funSpec.get()) }
  fun addProperty(propertySpec: PropertySpecSupplier) = builder { this.addProperty(propertySpec.get()) }

  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }
  fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

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
}


typealias KotlinDataClassSpecBuilderReceiver = KotlinDataClassSpecBuilder.() -> Unit
