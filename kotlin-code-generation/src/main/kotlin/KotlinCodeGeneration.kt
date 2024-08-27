@file:Suppress(SUPPRESS_UNUSED)

package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.anonymousClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.classBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.companionObjectBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.fileBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.funBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.interfaceBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.objectBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.parameterBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.propertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.typeAliasBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.valueClassBuilder
import io.toolisticon.kotlin.generation.builder.*
import io.toolisticon.kotlin.generation.poet.FormatSpecifier.asCodeBlock
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationServiceLoader
import io.toolisticon.kotlin.generation.support.SUPPRESS_CLASS_NAME
import io.toolisticon.kotlin.generation.support.SUPPRESS_MEMBER_VISIBILITY_CAN_BE_PRIVATE
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
object KotlinCodeGeneration {

  inline fun buildAnnotation(type: KClass<*>, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = buildAnnotation(type.asClassName(), block)
  inline fun buildAnnotation(className: ClassName, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = annotationBuilder(className).also(block).build()

  inline fun buildAnnotationClass(className: ClassName, block: KotlinAnnotationClassSpecBuilderReceiver = {}): KotlinAnnotationClassSpec = annotationClassBuilder(className).also(block).build()
  inline fun buildAnnotationClass(packageName: PackageName, simpleName: SimpleName, block: KotlinAnnotationClassSpecBuilderReceiver = {}): KotlinAnnotationClassSpec =
    buildAnnotationClass(className(packageName, simpleName), block)

  inline fun buildAnonymousClass(block: KotlinAnonymousClassSpecBuilderReceiver = {}) = anonymousClassBuilder().also(block).build()

  inline fun buildClass(className: ClassName, block: KotlinClassSpecBuilderReceiver = {}) = classBuilder(className).also(block).build()
  inline fun buildClass(packageName: PackageName, simpleName: SimpleName, block: KotlinClassSpecBuilderReceiver = {}) = buildClass(className(packageName, simpleName), block)

  fun buildCodeBlock(format: CodeBlockFormat, vararg args: Any?) = CodeBlock.of(format, *args)
  inline fun buildCodeBlock(builderAction: CodeBlock.Builder.() -> Unit): CodeBlock = CodeBlock.builder().apply(builderAction).build()

  inline fun buildCompanionObject(name: String? = null, block: KotlinCompanionObjectSpecBuilderReceiver = {}) = companionObjectBuilder(name).also(block).build()

  inline fun buildConstructorProperty(name: PropertyName, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = constructorPropertyBuilder(name, type).also(block).build()
  inline fun buildConstructorProperty(name: PropertyName, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = buildConstructorProperty(name, type.asTypeName(), block)

  inline fun buildDataClass(className: ClassName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec = KotlinDataClassSpecBuilder.builder(className).also(block).build()
  inline fun buildDataClass(packageName: PackageName, simpleName: SimpleName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec =
    buildDataClass(className(packageName, simpleName), block)

  inline fun buildEnumClass(className: ClassName, block: KotlinEnumClassSpecBuilderReceiver = {}) = KotlinEnumClassSpecBuilder.builder(className).also(block).build()
  inline fun buildEnumClass(packageName: PackageName, simpleName: SimpleName, block: KotlinEnumClassSpecBuilderReceiver = {}) = buildEnumClass(className(packageName, simpleName), block)

  inline fun buildFile(className: ClassName, block: KotlinFileSpecBuilderReceiver = {}): KotlinFileSpec = fileBuilder(className).also(block).build()
  inline fun buildFile(packageName: PackageName, simpleName: SimpleName, block: KotlinFileSpecBuilderReceiver = {}): KotlinFileSpec = buildFile(className(packageName, simpleName), block)

  inline fun buildFun(name: FunctionName, block: KotlinFunSpecBuilderReceiver = {}): KotlinFunSpec = funBuilder(name).also(block).build()

  inline fun buildInterface(className: ClassName, block: KotlinInterfaceSpecBuilderReceiver = {}): KotlinInterfaceSpec = interfaceBuilder(className).also(block).build()
  inline fun buildInterface(packageName: PackageName, simpleName: SimpleName, block: KotlinInterfaceSpecBuilderReceiver = {}): KotlinInterfaceSpec =
    buildInterface(className(packageName, simpleName), block)

  inline fun buildObject(className: ClassName, block: KotlinObjectSpecBuilderReceiver = {}): KotlinObjectSpec = objectBuilder(className).also(block).build()
  inline fun buildObject(packageName: PackageName, simpleName: SimpleName, block: KotlinObjectSpecBuilderReceiver = {}): KotlinObjectSpec = buildObject(className(packageName, simpleName), block)

  inline fun buildParameter(name: ParameterName, typeName: TypeName, block: KotlinParameterSpecBuilderReceiver = {}): KotlinParameterSpec = parameterBuilder(name, typeName).also(block).build()
  inline fun buildParameter(name: ParameterName, type: KClass<*>, block: KotlinParameterSpecBuilderReceiver = {}): KotlinParameterSpec = buildParameter(name, type.asTypeName(), block)

  inline fun buildProperty(name: PropertyName, typeName: TypeName, block: KotlinPropertySpecBuilderReceiver = {}): KotlinPropertySpec = propertyBuilder(name, typeName).also(block).build()
  inline fun buildProperty(name: PropertyName, type: KClass<*>, block: KotlinPropertySpecBuilderReceiver = {}): KotlinPropertySpec = buildProperty(name, type.asTypeName(), block)

  inline fun buildTypeAlias(name: TypeAliasName, type: TypeName, block: KotlinTypeAliasSpecBuilderReceiver = {}): KotlinTypeAliasSpec = typeAliasBuilder(name, type).also(block).build()

  inline fun buildValueClass(className: ClassName, block: KotlinValueClassSpecBuilderReceiver = {}): KotlinValueClassSpec = valueClassBuilder(className).also(block).build()
  inline fun buildValueClass(packageName: PackageName, simpleName: SimpleName, block: KotlinValueClassSpecBuilderReceiver = {}): KotlinValueClassSpec =
    buildValueClass(className(packageName, simpleName), block)

  fun toFileSpec(spec: KotlinValueClassSpecSupplier): KotlinFileSpec = spec.spec().let {
    KotlinFileSpecBuilder.builder(it.className).addType(it).build()
  }

  @ExperimentalKotlinPoetApi
  @Suppress(SUPPRESS_CLASS_NAME, SUPPRESS_MEMBER_VISIBILITY_CAN_BE_PRIVATE)
  object builder {
    fun annotationClassBuilder(className: ClassName) = KotlinAnnotationClassSpecBuilder.builder(className)
    fun annotationClassBuilder(packageName: PackageName, simpleName: SimpleName) = annotationClassBuilder(className(packageName, simpleName))

    fun annotationBuilder(type: ClassName) = KotlinAnnotationSpecBuilder.builder(type)
    fun annotationBuilder(packageName: PackageName, simpleName: SimpleName) = annotationBuilder(className(packageName, simpleName))

    fun anonymousClassBuilder() = KotlinAnonymousClassSpecBuilder.builder()

    fun classBuilder(className: ClassName) = KotlinClassSpecBuilder.builder(className)
    fun classBuilder(packageName: PackageName, simpleName: SimpleName) = classBuilder(className(packageName, simpleName))

    fun companionObjectBuilder(name: String? = null) = KotlinCompanionObjectSpecBuilder.builder(name)

    fun constructorPropertyBuilder(name: PropertyName, type: TypeName) = KotlinConstructorPropertySpecBuilder.builder(name, type)

    fun dataClassBuilder(className: ClassName) = KotlinDataClassSpecBuilder.builder(className)
    fun dataClassBuilder(packageName: PackageName, simpleName: SimpleName) = dataClassBuilder(className(packageName, simpleName))

    fun enumClassBuilder(name: SimpleName) = KotlinEnumClassSpecBuilder.builder(name)
    fun enumClassBuilder(packageName: PackageName, name: SimpleName) = enumClassBuilder(className(packageName, name))
    fun enumClassBuilder(className: ClassName) = KotlinEnumClassSpecBuilder.builder(className)

    fun fileBuilder(className: ClassName) = KotlinFileSpecBuilder.builder(className)
    fun fileBuilder(packageName: PackageName, simpleName: SimpleName) = fileBuilder(className(packageName, simpleName))

    fun funBuilder(name: FunctionName) = KotlinFunSpecBuilder.builder(name)

    fun interfaceBuilder(className: ClassName) = KotlinInterfaceSpecBuilder.builder(className)
    fun interfaceBuilder(packageName: PackageName, simpleName: SimpleName) = interfaceBuilder(className(packageName, simpleName))

    fun objectBuilder(className: ClassName) = KotlinObjectSpecBuilder.builder(className)
    fun objectBuilder(packageName: PackageName, simpleName: SimpleName) = objectBuilder(className(packageName, simpleName))

    fun parameterBuilder(name: ParameterName, type: TypeName) = KotlinParameterSpecBuilder.builder(name, type)
    fun parameterBuilder(name: ParameterName, type: KClass<*>) = parameterBuilder(name, type.asTypeName())

    fun propertyBuilder(name: PropertyName, type: TypeName) = KotlinPropertySpecBuilder.builder(name, type)
    fun propertyBuilder(name: PropertyName, type: KClass<*>) = propertyBuilder(name, type.asTypeName())

    fun typeAliasBuilder(name: TypeAliasName, type: TypeName): KotlinTypeAliasSpecBuilder = KotlinTypeAliasSpecBuilder.builder(name, type)
    fun typeAliasBuilder(name: String, type: KClass<*>): KotlinTypeAliasSpecBuilder = KotlinTypeAliasSpecBuilder.builder(name, type)

    fun valueClassBuilder(className: ClassName) = KotlinValueClassSpecBuilder.builder(className)
    fun valueClassBuilder(packageName: PackageName, simpleName: SimpleName) = valueClassBuilder(className(packageName, simpleName))
  }

  fun className(packageName: PackageName, simpleName: SimpleName) = ClassName(packageName, simpleName)
  fun simpleClassName(simpleName: SimpleName) = className("", simpleName)

  @Suppress(SUPPRESS_CLASS_NAME)
  object spi {
    val defaultClassLoader: () -> ClassLoader = { Thread.currentThread().contextClassLoader }

    fun registry(
      contextTypeUpperBound: KClass<*> = Any::class,
      classLoader: ClassLoader = defaultClassLoader()
    ): KotlinCodeGenerationSpiRegistry = KotlinCodeGenerationServiceLoader(contextTypeUpperBound = contextTypeUpperBound, classLoader = classLoader).invoke()
  }

  @Suppress(SUPPRESS_CLASS_NAME)
  object typeSpec {

    fun TypeSpec.hasModifier(modifier: KModifier) = this.modifiers.contains(modifier)

    val TypeSpec.isDataClass: Boolean get() = hasModifier(KModifier.DATA)
    val TypeSpec.isValueClass: Boolean get() = hasModifier(KModifier.VALUE)
  }

  @Suppress(SUPPRESS_CLASS_NAME)
  object name {
    fun Collection<MemberName>.asCodeBlock(): CodeBlock = this.map { it.asCodeBlock() }.joinToCode(prefix = "[", suffix = "]")

    fun Enum<*>.asMemberName(): MemberName = this::class.asClassName().member(this.name)

    // FIXME: remove?
    @Deprecated("not usable this way, fix or remove")
    operator fun ClassName.plus(suffix: String?): ClassName = ClassName(this.packageName, this.simpleNames)

    fun TypeName.nullable(nullable: Boolean = true): TypeName = this.copy(nullable = nullable)
  }

  @Suppress(SUPPRESS_CLASS_NAME)
  object format {
    const val FORMAT_STRING = "%S"
    const val FORMAT_STRING_TEMPLATE = "%P"
    const val FORMAT_TYPE = "%T"
    const val FORMAT_MEMBER = "%M"
    const val FORMAT_NAME = "%N"
    const val FORMAT_LITERAL = "%L"

    const val FORMAT_KCLASS = "$FORMAT_TYPE::class"

    const val NBSP = "·"
  }
}
