package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.TypeSpec.Builder
import io.toolisticon.kotlin.generation.BuilderSupplier
import kotlin.reflect.KClass

typealias TypeSpecBuilderReceiver = TypeSpecBuilder.() -> Unit

@JvmInline
@OptIn(ExperimentalKotlinPoetApi::class)
value class TypeSpecBuilder(private val builder: Builder) : BuilderSupplier<TypeSpec, Builder>,
    AnnotatableBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    ContextReceivableBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    DocumentableBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    KModifierHolderBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    MemberSpecHolderBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    OriginatingElementsHolderBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    TaggableBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    TypeSpecHolderBuilder<TypeSpecBuilder, TypeSpec, Builder>,
    TypeVariableHolderBuilder<TypeSpecBuilder, TypeSpec, Builder> {

  companion object {
    private fun Builder.wrap() = TypeSpecBuilder(this)
    fun annotationBuilder(name: String): TypeSpecBuilder = TypeSpec.annotationBuilder(name).wrap()
    fun annotationBuilder(className: ClassName): TypeSpecBuilder = annotationBuilder(className.simpleName)
    fun anonymousClassBuilder(): TypeSpecBuilder = TypeSpec.anonymousClassBuilder().wrap()
    fun classBuilder(name: String): TypeSpecBuilder = TypeSpec.classBuilder(name).wrap()
    fun classBuilder(className: ClassName): TypeSpecBuilder = classBuilder(className.simpleName)
    fun companionObjectBuilder(name: String? = null): TypeSpecBuilder = TypeSpec.companionObjectBuilder(name).wrap()
    fun enumBuilder(name: String): TypeSpecBuilder = TypeSpec.enumBuilder(name).wrap()
    fun enumBuilder(className: ClassName): TypeSpecBuilder = TypeSpec.enumBuilder(className).wrap()
    fun funInterfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.funInterfaceBuilder(name).wrap()
    fun funInterfaceBuilder(className: ClassName): TypeSpecBuilder = TypeSpec.funInterfaceBuilder(className).wrap()
    fun interfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.interfaceBuilder(name).wrap()
    fun interfaceBuilder(className: ClassName): TypeSpecBuilder = TypeSpec.interfaceBuilder(className).wrap()
    fun objectBuilder(name: String): TypeSpecBuilder = TypeSpec.objectBuilder(name).wrap()
    fun objectBuilder(className: ClassName): TypeSpecBuilder = objectBuilder(className.simpleName)
  }

  val enumConstants: MutableMap<String, TypeSpec> get() = builder.enumConstants
  val funSpecs: MutableList<FunSpec> get() = builder.funSpecs
  override val modifiers: MutableList<KModifier> get() = builder.modifiers.toMutableList()
  val propertySpecs: MutableList<PropertySpec> get() = builder.propertySpecs
  val superclassConstructorParameters: MutableList<CodeBlock> get() = builder.superclassConstructorParameters
  val superinterfaces: MutableMap<TypeName, CodeBlock?> get() = builder.superinterfaces
  val typeSpecs: MutableList<TypeSpec> get() = builder.typeSpecs
  override val typeVariables: MutableList<TypeVariableName> get() = builder.typeVariables

  fun addEnumConstant(name: String, typeSpec: TypeSpec = anonymousClassBuilder().build()): TypeSpecBuilder = apply { builder.addEnumConstant(name, typeSpec) }
  override fun addFunction(funSpec: FunSpec): TypeSpecBuilder = apply { builder.addFunction(funSpec) }
  fun addInitializerBlock(block: CodeBlock): TypeSpecBuilder = apply { builder.addInitializerBlock(block) }
  fun addInitializerBlock(builder: CodeBlockBuilder): TypeSpecBuilder = addInitializerBlock(builder.build())
  override fun addProperty(propertySpec: PropertySpec): TypeSpecBuilder = apply { builder.addProperty(propertySpec) }
  fun addSuperclassConstructorParameter(format: String, vararg args: Any): TypeSpecBuilder = apply { builder.addSuperclassConstructorParameter(format, *args) }
  fun addSuperclassConstructorParameter(codeBlock: CodeBlock): TypeSpecBuilder = apply { builder.addSuperclassConstructorParameter(codeBlock) }
  fun addSuperclassConstructorParameter(builder: CodeBlockBuilder): TypeSpecBuilder = addSuperclassConstructorParameter(builder.build())
  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock): TypeSpecBuilder = addSuperinterface(superinterface.asTypeName(), delegate)
  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String): TypeSpecBuilder = addSuperinterface(superinterface.asTypeName(), constructorParameterName)
  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface, delegate) }
  fun addSuperinterface(superinterface: TypeName, constructorParameter: String): TypeSpecBuilder = apply { builder.addSuperinterface(superinterface, constructorParameter) }
  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>): TypeSpecBuilder = apply { builder.addSuperinterfaces(superinterfaces) }
  override fun addType(typeSpec: TypeSpec): TypeSpecBuilder = apply { builder.addType(typeSpec) }
  fun primaryConstructor(primaryConstructor: FunSpec): TypeSpecBuilder = apply { builder.primaryConstructor(primaryConstructor) }
  fun superclass(superclass: TypeName): TypeSpecBuilder = apply { builder.superclass(superclass) }
  fun superclass(superclass: KClass<*>): TypeSpecBuilder = superclass(superclass.asTypeName())

  override fun build(): TypeSpec = builder.build()
  override fun get(): Builder = builder
}
