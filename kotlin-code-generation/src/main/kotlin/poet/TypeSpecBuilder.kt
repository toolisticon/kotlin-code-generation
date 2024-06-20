package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * TypeSpec includes:
 *
 * * AnnotationClassSpec
 * * AnonymousClassSpec
 * * ClassSpec
 * * CompanionObjectSpec
 * * DataClassSpec
 * * EnumClassSpec
 * * InterfaceSpec
 * * ObjectSpec
 * * ValueClassSpec
 */
class TypeSpecBuilder(
  override val builder: TypeSpec.Builder
) : PoetSpecBuilder<TypeSpecBuilder, TypeSpec.Builder, TypeSpec, TypeSpecSupplier>,
  AnnotatableBuilder<TypeSpecBuilder>,
  ContextReceivableBuilder<TypeSpecBuilder>,
  DocumentableBuilder<TypeSpecBuilder>,
  MemberSpecHolderBuilder<TypeSpecBuilder>,
  OriginatingElementsHolderBuilder<TypeSpecBuilder>,
  TypeSpecHolderBuilder<TypeSpecBuilder> {
  companion object {
    fun TypeSpec.Builder.wrap() = TypeSpecBuilder(builder = this)

    @JvmStatic
    fun classBuilder(name: String): TypeSpecBuilder = TypeSpec.classBuilder(name).wrap()

    @JvmStatic
    fun classBuilder(className: ClassName): TypeSpecBuilder = classBuilder(className.simpleName)

    @JvmStatic
    fun objectBuilder(name: String): TypeSpecBuilder = TypeSpec.objectBuilder(name).wrap()

    @JvmStatic
    fun objectBuilder(className: ClassName): TypeSpecBuilder = objectBuilder(className.simpleName)

    @JvmStatic
    @JvmOverloads
    fun companionObjectBuilder(name: String? = null): TypeSpecBuilder = TypeSpec.companionObjectBuilder(name).wrap()

    @JvmStatic
    fun interfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.interfaceBuilder(name).wrap()

    @JvmStatic
    fun interfaceBuilder(className: ClassName): TypeSpecBuilder = interfaceBuilder(className.simpleName)

    @JvmStatic
    fun funInterfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.funInterfaceBuilder(name).wrap()

    @JvmStatic
    fun funInterfaceBuilder(className: ClassName): TypeSpecBuilder = funInterfaceBuilder(className.simpleName)

    @JvmStatic
    fun enumBuilder(name: String): TypeSpecBuilder = TypeSpec.enumBuilder(name).wrap()

    @JvmStatic
    fun enumBuilder(className: ClassName): TypeSpecBuilder = enumBuilder(className.simpleName)

    @JvmStatic
    fun anonymousClassBuilder(): TypeSpecBuilder = TypeSpec.anonymousClassBuilder().wrap()

    @JvmStatic
    fun annotationBuilder(name: String): TypeSpecBuilder = TypeSpec.annotationBuilder(name).wrap()

    @JvmStatic
    fun annotationBuilder(className: ClassName): TypeSpecBuilder = annotationBuilder(className.simpleName)
  }


  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = invoke { addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = invoke { addAnnotations(annotationSpecs) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = invoke { addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = invoke { addKdoc(block) }

  // ContextReceiverBuilder
  @ExperimentalKotlinPoetApi
  override fun contextReceivers(receiverTypes: Iterable<TypeName>) = invoke { contextReceivers(receiverTypes) }

  @ExperimentalKotlinPoetApi
  override fun contextReceivers(vararg receiverTypes: TypeName) = invoke { contextReceivers(*receiverTypes) }

  // MemberSpecHolderBuilder
  override fun addFunction(funSpec: FunSpec) = invoke { addFunction(funSpec) }
  override fun addFunctions(funSpecs: Iterable<FunSpec>) = invoke { addFunctions(funSpecs) }
  override fun addProperty(propertySpec: PropertySpec) = invoke { addProperty(propertySpec) }
  override fun addProperties(propertySpecs: Iterable<PropertySpec>) = invoke { addProperties(propertySpecs) }

  // OriginatingElementBuilder
  override fun addOriginatingElement(originatingElement: Element) = invoke { addOriginatingElement(originatingElement) }

  // TypeSpecHolderBuilder
  override fun addType(typeSpec: TypeSpec) = invoke { addType(typeSpec) }
  override fun addTypes(typeSpecs: Iterable<TypeSpec>) = invoke { addTypes(typeSpecs) }


  fun addModifiers(vararg modifiers: KModifier): TypeSpecBuilder = invoke { addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): TypeSpecBuilder = invoke { addModifiers(modifiers) }
  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): TypeSpecBuilder = invoke { addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName): TypeSpecBuilder = invoke { addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpec?): TypeSpecBuilder = invoke { primaryConstructor(primaryConstructor) }
  fun superclass(superclass: TypeName): TypeSpecBuilder = invoke { superclass(superclass) }
  fun superclass(superclass: KClass<*>): TypeSpecBuilder = invoke { superclass(superclass) }

  fun addSuperclassConstructorParameter(format: String, vararg args: Any): TypeSpecBuilder = invoke { addSuperclassConstructorParameter(format, *args) }
  fun addSuperclassConstructorParameter(codeBlock: CodeBlock): TypeSpecBuilder = invoke { addSuperclassConstructorParameter(codeBlock) }

  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>): TypeSpecBuilder = invoke { addSuperinterfaces(superinterfaces) }
  fun addSuperinterface(superinterface: TypeName): TypeSpecBuilder = invoke { addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock): TypeSpecBuilder = invoke { addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>): TypeSpecBuilder = invoke { addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock): TypeSpecBuilder = invoke { addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String): TypeSpecBuilder = invoke { addSuperinterface(superinterface, constructorParameterName) }
  fun addSuperinterface(superinterface: TypeName, constructorParameter: String): TypeSpecBuilder = invoke { addSuperinterface(superinterface, constructorParameter) }

  fun addEnumConstant(name: String, typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build()): TypeSpecBuilder = invoke { addEnumConstant(name, typeSpec) }
  fun addInitializerBlock(block: CodeBlock): TypeSpecBuilder = invoke { addInitializerBlock(block) }

  override fun invoke(block: TypeSpecBuilderReceiver): TypeSpecBuilder = apply {
    builder.block()
  }

  override fun build(): TypeSpec = builder.build()
}

interface TypeSpecSupplier : PoetSpecSupplier<TypeSpec>
typealias TypeSpecBuilderReceiver = TypeSpec.Builder.() -> Unit
