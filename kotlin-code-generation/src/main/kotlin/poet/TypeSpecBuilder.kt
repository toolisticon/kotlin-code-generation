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
    internal fun TypeSpec.Builder.wrap() = TypeSpecBuilder(builder = this)

    fun annotationBuilder(name: String): TypeSpecBuilder = TypeSpec.annotationBuilder(name).wrap()

    fun annotationBuilder(className: ClassName): TypeSpecBuilder = annotationBuilder(className.simpleName)

    fun classBuilder(name: String): TypeSpecBuilder = TypeSpec.classBuilder(name).wrap()

    fun classBuilder(className: ClassName): TypeSpecBuilder = classBuilder(className.simpleName)

    fun objectBuilder(name: String): TypeSpecBuilder = TypeSpec.objectBuilder(name).wrap()

    fun objectBuilder(className: ClassName): TypeSpecBuilder = objectBuilder(className.simpleName)

    fun companionObjectBuilder(name: String? = null): TypeSpecBuilder = TypeSpec.companionObjectBuilder(name).wrap()

    fun interfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.interfaceBuilder(name).wrap()

    fun interfaceBuilder(className: ClassName): TypeSpecBuilder = interfaceBuilder(className.simpleName)

    fun funInterfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.funInterfaceBuilder(name).wrap()

    fun funInterfaceBuilder(className: ClassName): TypeSpecBuilder = funInterfaceBuilder(className.simpleName)

    fun enumBuilder(name: String): TypeSpecBuilder = TypeSpec.enumBuilder(name).wrap()

    fun enumBuilder(className: ClassName): TypeSpecBuilder = enumBuilder(className.simpleName)

    fun anonymousClassBuilder(): TypeSpecBuilder = TypeSpec.anonymousClassBuilder().wrap()
  }


  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = apply { builder.addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = apply { builder.addAnnotations(annotationSpecs) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = apply { builder.addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = apply { builder.addKdoc(block) }

  // ContextReceiverBuilder
  override fun contextReceivers(receiverTypes: Iterable<TypeName>) = apply { builder.contextReceivers(receiverTypes) }
  override fun contextReceivers(vararg receiverTypes: TypeName) = apply { builder.contextReceivers(*receiverTypes) }

  // MemberSpecHolderBuilder
  override fun addFunction(funSpec: FunSpec) = apply { builder.addFunction(funSpec) }
  override fun addFunctions(funSpecs: Iterable<FunSpec>) = apply { builder.addFunctions(funSpecs) }
  override fun addProperty(propertySpec: PropertySpec) = apply { builder.addProperty(propertySpec) }
  override fun addProperties(propertySpecs: Iterable<PropertySpec>) = apply { builder.addProperties(propertySpecs) }

  // OriginatingElementBuilder
  override fun addOriginatingElement(originatingElement: Element) = apply { builder.addOriginatingElement(originatingElement) }

  // TypeSpecHolderBuilder
  override fun addType(typeSpec: TypeSpec) = apply { builder.addType(typeSpec) }
  override fun addTypes(typeSpecs: Iterable<TypeSpec>) = apply { builder.addTypes(typeSpecs) }


  fun addModifiers(vararg modifiers: KModifier): TypeSpecBuilder = apply { builder.addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): TypeSpecBuilder = apply { builder.addModifiers(modifiers) }
  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): TypeSpecBuilder = apply { builder.addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName): TypeSpecBuilder = apply { builder.addTypeVariable(typeVariable) }
  fun primaryConstructor(primaryConstructor: FunSpec?): TypeSpecBuilder = apply { builder.primaryConstructor(primaryConstructor) }
  fun superclass(superclass: TypeName): TypeSpecBuilder = apply { builder.superclass(superclass) }
  fun superclass(superclass: KClass<*>): TypeSpecBuilder = apply { builder.superclass(superclass) }

  fun addSuperclassConstructorParameter(format: String, vararg args: Any): TypeSpecBuilder = apply { builder.addSuperclassConstructorParameter(format, *args) }
  fun addSuperclassConstructorParameter(codeBlock: CodeBlock): TypeSpecBuilder = apply { builder.addSuperclassConstructorParameter(codeBlock) }

  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>): TypeSpecBuilder = apply { builder.addSuperinterfaces(superinterfaces) }
  fun addSuperinterface(superinterface: TypeName): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface) }
  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface, constructorParameterName) }
  fun addSuperinterface(superinterface: TypeName, constructorParameter: String): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface, constructorParameter) }

  fun addEnumConstant(name: String, typeSpec: TypeSpec = TypeSpec.anonymousClassBuilder().build()): TypeSpecBuilder = apply { builder.addEnumConstant(name, typeSpec) }
  fun addInitializerBlock(block: CodeBlock): TypeSpecBuilder = apply { builder.addInitializerBlock(block) }

  override fun build(): TypeSpec = builder.build()
}

interface TypeSpecSupplier : PoetSpecSupplier<TypeSpec>
typealias TypeSpecBuilderReceiver = TypeSpec.Builder.() -> Unit
