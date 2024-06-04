package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.*

@JvmInline
@ExperimentalKotlinPoetApi
value class TypeSpecBuilder(private val builder: TypeSpec.Builder) : KotlinPoetBuilderSupplier<TypeSpec, TypeSpec.Builder>,
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

  override fun get(): TypeSpec.Builder = builder

  override fun build(): TypeSpec = builder.build()
}
