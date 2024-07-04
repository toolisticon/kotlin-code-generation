package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.*
import javax.lang.model.element.Element
import kotlin.reflect.KClass


class KotlinClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinClassSpecBuilder, KotlinClassSpec> {

  companion object {
    fun builder(name: String): KotlinClassSpecBuilder = KotlinClassSpecBuilder(
      className = ClassName("", name),
      delegate = TypeSpecBuilder.classBuilder(name)
    )

    fun builder(className: ClassName): KotlinClassSpecBuilder = KotlinClassSpecBuilder(
      className = className,
      delegate = TypeSpecBuilder.classBuilder(className.simpleName)
    )
  }

  fun addKdoc(format: String, vararg args: Any) = builder { addKdoc(format, *args) }
  fun addKdoc(block: CodeBlock) = builder { addKdoc(block) }

  fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = builder {
    delegate.addAnnotation(spec.get())
  }

  fun addFunction(funSpec : KotlinFunSpecSupplier) = apply {
    delegate.addFunction(funSpec.get())
  }


  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }


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

  override fun build(): KotlinClassSpec = KotlinClassSpec(
    className = className,
    spec = delegate.build()
  )

//  operator fun invoke(spec: KotlinDataClassSpec): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder =
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder(
//      className = spec.className,
//      delegate = spec.get().toBuilder()
//    )
//}
//
//private val constructorProperties = LinkedHashMap<String, ConstructorPropertySupplier>()
//
//operator fun invoke(block: TypeSpecBuilder.() -> Unit): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder = apply {
//  delegate.block()
//  override fun addKdoc(kdoc: CodeBlock) = apply {
//    delegate.addKdoc(kdoc)
//  }
//
//  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
//    delegate.addType(typeSpecSupplier.get())
//  }
//
//  fun addConstructorProperty(name: String, type: TypeName, block: _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinConstructorPropertyBuilder.() -> Unit = {}) = apply {
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties[name] = buildConstructorProperty(name, type, block)
//  }
//
//  fun addConstructorProperty(constructorProperty: ConstructorPropertySupplier) = apply {
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties[constructorProperty.name] = constructorProperty
//  }
//
//  /**
//   * Finalize a data class based on its primary constructor parameters.
//   *
//   * * adds primary constructor.
//   * * backs parameters with properties.
//   */
//  override fun build(): KotlinDataClassSpec {
//    check(_root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties.isNotEmpty()) { "Data class must have at least one property." }
//
//    val constructor = FunSpec.constructorBuilder()
//
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties.values.map(ConstructorPropertySupplier::get).forEach {
//      constructor.addParameter(it.parameter.get())
//      delegate.addProperty(it.property.get())
//    }
//
//    delegate.primaryConstructor(constructor.build())
//
//    return KotlinDataClassSpec(className = className, spec = delegate.build())
//  }
//
//  override fun addAnnotation(annotation: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder = apply {
//    fun addAnnotation(annotation: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder = apply {
//      delegate.addAnnotation(annotation)
//    }
//
//    override fun get(): TypeSpec = build().get()
//
//  }
}


//  @Suppress("ClassName")
//  object builder :
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.ToKotlinPoetSpecBuilder<KotlinDataClassSpec, _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder> {
//
//    fun builder(className: ClassName) = _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder(className = className)
//    operator fun invoke(packageName: String, name: String): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder =
//      _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.builder.invoke(ClassName(packageName, name))
//
//    fun builder(packageName: String, name: String) =
//      _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.builder.builder(ClassName(packageName, name))
//    operator fun invoke(className: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder =
//      _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder(
//        className = className,
//        delegate = TypeSpec.classBuilder(className)
//      )
//
//  }

typealias KotlinClassSpecBuilderReceiver = KotlinClassSpecBuilder.() -> Unit
