package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.classBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.fileBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.funBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.propertyBuilder
import io.toolisticon.kotlin.generation.builder.*
import io.toolisticon.kotlin.generation.poet.FormatSpecifier.asCodeBlock
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.AbstractKotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationServiceRepository
import io.toolisticon.kotlin.generation.support.SuppressAnnotation.Companion.CLASS_NAME
import io.toolisticon.kotlin.generation.support.SuppressAnnotation.Companion.MEMBER_VISIBILITY_CAN_BE_PRIVATE
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
object KotlinCodeGeneration {

  inline fun buildAnnotation(type: KClass<*>, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = buildAnnotation(type.asClassName(), block)
  inline fun buildAnnotation(className: ClassName, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = annotationBuilder(className).also(block).build()
  inline fun buildAnnotationClass(className: ClassName, block: KotlinAnnotationClassSpecBuilderReceiver = {}): KotlinAnnotationClassSpec = annotationClassBuilder(className).also(block).build()

  inline fun buildClass(className: ClassName, block: KotlinClassSpecBuilderReceiver = {}) = classBuilder(className).also(block).build()

  fun buildCodeBlock(format: CodeBlockFormat, vararg args: Any?) = CodeBlock.of(format, *args)
  inline fun buildCodeBlock(builderAction: CodeBlock.Builder.() -> Unit): CodeBlock = CodeBlock.builder().apply(builderAction).build()

  inline fun buildConstructorProperty(name: PropertyName, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = constructorPropertyBuilder(name, type).also(block).build()
  inline fun buildConstructorProperty(name: PropertyName, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = buildConstructorProperty(name, type.asTypeName(), block)

  inline fun buildDataClass(className: ClassName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec = KotlinDataClassSpecBuilder.builder(className).also(block).build()

  inline fun buildFile(className: ClassName, block: KotlinFileSpecBuilderReceiver = {}): KotlinFileSpec = fileBuilder(className).also(block).build()

  inline fun buildFun(name: FunctionName, block: KotlinFunSpecBuilderReceiver = {}): KotlinFunSpec = funBuilder(name).also(block).build()

  inline fun buildProperty(name: PropertyName, typeName: TypeName, block: KotlinPropertySpecBuilderReceiver = {}): KotlinPropertySpec = propertyBuilder(name, typeName).also(block).build()
  inline fun buildProperty(name: PropertyName, type: KClass<*>, block: KotlinPropertySpecBuilderReceiver = {}): KotlinPropertySpec = buildProperty(name, type.asTypeName(), block)

  fun buildValueClass(className: ClassName, block: KotlinValueClassSpecBuilderReceiver = {}): KotlinValueClassSpec = KotlinValueClassSpecBuilder.builder(className).also(block).build()

  fun toFileSpec(spec: KotlinValueClassSpecSupplier): KotlinFileSpec = spec.spec().let {
    KotlinFileSpecBuilder.builder(it.className).addType(it).build()
  }

  @ExperimentalKotlinPoetApi
  @Suppress(CLASS_NAME, MEMBER_VISIBILITY_CAN_BE_PRIVATE)
  object builder {
    fun annotationBuilder(type: ClassName) = KotlinAnnotationSpecBuilder.builder(type)
    fun annotationBuilder(packageName: PackageName, simpleName: SimpleName) = annotationBuilder(className(packageName, simpleName))

    fun annotationClassBuilder(className: ClassName) = KotlinAnnotationClassSpecBuilder.builder(className)
    fun annotationClassBuilder(packageName: PackageName, simpleName: SimpleName) = annotationClassBuilder(className(packageName, simpleName))

    fun classBuilder(className: ClassName) = KotlinClassSpecBuilder.builder(className);
    fun classBuilder(packageName: PackageName, simpleName: SimpleName) = classBuilder(className(packageName, simpleName))

    fun constructorPropertyBuilder(name: PropertyName, type: TypeName) = KotlinConstructorPropertySpecBuilder.builder(name, type)

    fun dataClassBuilder(className: ClassName) = KotlinDataClassSpecBuilder.builder(className)
    fun dataClassBuilder(packageName: PackageName, simpleName: SimpleName) = dataClassBuilder(className(packageName, simpleName))

    fun fileBuilder(className: ClassName) = KotlinFileSpecBuilder.builder(className)
    fun fileBuilder(packageName: PackageName, simpleName: SimpleName) = fileBuilder(className(packageName, simpleName))

    fun funBuilder(name: FunctionName) = KotlinFunSpecBuilder.builder(name)

    fun propertyBuilder(name: PropertyName, type: TypeName) = KotlinPropertySpecBuilder.builder(name, type)
    fun propertyBuilder(name: PropertyName, type: KClass<*>) = propertyBuilder(name, type.asTypeName())

    fun valueClassBuilder(className: ClassName) = KotlinValueClassSpecBuilder.builder(className)
    fun valueClassBuilder(packageName: PackageName, simpleName: SimpleName) = valueClassBuilder(className(packageName, simpleName))
  }

  fun className(packageName: PackageName, simpleName: SimpleName) = ClassName(packageName, simpleName)

  @Suppress(CLASS_NAME)
  object spi {
    val defaultClassLoader: () -> ClassLoader = { Thread.currentThread().contextClassLoader }

    fun repository(
      contextType: KClass<*>,
      classLoader: ClassLoader = defaultClassLoader()
    ): KotlinCodeGenerationSpiRegistry = KotlinCodeGenerationServiceRepository.load(contextType = contextType, classLoader = classLoader)
  }

  @Suppress(CLASS_NAME)
  object typeSpec {

    fun TypeSpec.hasModifier(modifier: KModifier) = this.modifiers.contains(modifier)

    val TypeSpec.isDataClass: Boolean get() = hasModifier(KModifier.DATA)
    val TypeSpec.isValueClass: Boolean get() = hasModifier(KModifier.VALUE)
  }

  @Suppress(CLASS_NAME)
  object name {
    fun Collection<MemberName>.asCodeBlock(): CodeBlock = this.map { it.asCodeBlock() }.joinToCode(prefix = "[", suffix = "]")

    fun Enum<*>.asMemberName(): MemberName = this::class.asClassName().member(this.name)

    operator fun ClassName.plus(suffix: String?): ClassName = ClassName(this.packageName, this.simpleNames)

    fun TypeName.nullable(nullable: Boolean = true): TypeName = this.copy(nullable = nullable)
  }

  @Suppress(CLASS_NAME)
  object format {
    const val FORMAT_STRING = "%S"
    const val FORMAT_STRING_TEMPLATE = "%P"
    const val FORMAT_TYPE = "%T"
    const val FORMAT_MEMBER = "%M"
    const val FORMAT_NAME = "%N"
    const val FORMAT_LITERAL = "%L"
  }
}
