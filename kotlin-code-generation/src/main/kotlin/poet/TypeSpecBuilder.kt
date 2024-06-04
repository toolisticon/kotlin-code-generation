package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.TypeSpec.Companion.anonymousClassBuilder
import io.toolisticon.kotlin.generation.BuilderSupplier
import kotlin.reflect.KClass

@JvmInline
@ExperimentalKotlinPoetApi
value class TypeSpecBuilder(private val builder: TypeSpec.Builder) : BuilderSupplier<TypeSpec, TypeSpec.Builder>,
  AnnotatableBuilder<TypeSpecBuilder, TypeSpec, TypeSpec.Builder>,
  ContextReceivableBuilder<TypeSpecBuilder, TypeSpec, TypeSpec.Builder>,
  DocumentableBuilder<TypeSpecBuilder, TypeSpec, TypeSpec.Builder>,
  MemberSpecHolderBuilder<TypeSpecBuilder, TypeSpec, TypeSpec.Builder>,
  OriginatingElementsHolderBuilder<TypeSpecBuilder, TypeSpec, TypeSpec.Builder>,
  TaggableBuilder<TypeSpecBuilder, TypeSpec, TypeSpec.Builder>,
  TypeSpecHolderBuilder<TypeSpecBuilder, TypeSpec, TypeSpec.Builder> {

  val enumConstants: MutableMap<String, TypeSpec> get() = builder.enumConstants
  val funSpecs: MutableList<FunSpec> get() = builder.funSpecs
  val modifiers: MutableSet<KModifier> get() = builder.modifiers
  val propertySpecs: MutableList<PropertySpec> get() = builder.propertySpecs
  val superclassConstructorParameters: MutableList<CodeBlock> get() = builder.superclassConstructorParameters
  val superinterfaces: MutableMap<TypeName, CodeBlock?> get() = builder.superinterfaces
  val typeSpecs: MutableList<TypeSpec> get() = builder.typeSpecs
  val typeVariables: MutableList<TypeVariableName> get() = builder.typeVariables

  override fun addProperty(propertySpec: PropertySpec): TypeSpecBuilder = apply {
    builder.addProperty(propertySpec)
  }

  override fun addFunction(funSpec: FunSpec): TypeSpecBuilder = apply {
    builder.addFunction(funSpec)
  }

  override fun addType(typeSpec: TypeSpec): TypeSpecBuilder = apply {
    builder.addType(typeSpec)
  }

  fun addModifiers(vararg modifiers: KModifier): TypeSpecBuilder = apply {
    builder.addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): TypeSpecBuilder = apply {
    builder.addModifiers(modifiers)
  }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): TypeSpecBuilder = apply {
    builder.addTypeVariables(typeVariables)
  }

  fun addTypeVariable(typeVariable: TypeVariableName): TypeSpecBuilder = apply {
    builder.addTypeVariable(typeVariable)
  }

  fun primaryConstructor(primaryConstructor: FunSpec): TypeSpecBuilder = apply {
    builder.primaryConstructor(primaryConstructor)
  }

  fun superclass(superclass: TypeName): TypeSpecBuilder = apply {
    builder.superclass(superclass)
  }

  fun superclass(superclass: KClass<*>): TypeSpecBuilder = superclass(superclass.asTypeName())

  fun addSuperclassConstructorParameter(format: String, vararg args: Any): TypeSpecBuilder = apply {
    builder.addSuperclassConstructorParameter(format, *args)
  }

  fun addSuperclassConstructorParameter(codeBlock: CodeBlock): TypeSpecBuilder = apply {
    builder.addSuperclassConstructorParameter(codeBlock)
  }

  fun addSuperclassConstructorParameter(builder: CodeBlockBuilder): TypeSpecBuilder = addSuperclassConstructorParameter(builder.build())

  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>): TypeSpecBuilder = apply {
    builder.addSuperinterfaces(superinterfaces)
  }

  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock): TypeSpecBuilder = apply {
    builder.addSuperinterface(superinterface, delegate)
  }

  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock): TypeSpecBuilder = addSuperinterface(superinterface.asTypeName(), delegate)

  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String): TypeSpecBuilder = addSuperinterface(superinterface.asTypeName(), constructorParameterName)

  fun addSuperinterface(superinterface: TypeName, constructorParameter: String): TypeSpecBuilder = apply {
    builder.addSuperinterface(superinterface, constructorParameter)
  }

  fun addEnumConstant(name: String, typeSpec: TypeSpec = anonymousClassBuilder().build()): TypeSpecBuilder = apply {
    builder.addEnumConstant(name, typeSpec)
  }

  fun addInitializerBlock(block: CodeBlock): TypeSpecBuilder = apply {
    builder.addInitializerBlock(block)
  }

  fun addInitializerBlock(builder: CodeBlockBuilder): TypeSpecBuilder = addInitializerBlock(builder.build())

  override fun get(): TypeSpec.Builder = builder

  override fun build(): TypeSpec = builder.build()
}
