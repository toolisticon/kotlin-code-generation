@file:Suppress("unused")

package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.anonymousClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.classBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.companionObjectBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.delegateListValueClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.delegateMapValueClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.fileBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.funBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.interfaceBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.objectBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.parameterBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.propertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.runtimeExceptionClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.typeAliasBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.valueClassBuilder
import io.toolisticon.kotlin.generation.builder.*
import io.toolisticon.kotlin.generation.builder.extra.*
import io.toolisticon.kotlin.generation.builder.extra.DelegateMapValueClassSpecBuilder.Companion.DEFAULT_KEY_TYPE
import io.toolisticon.kotlin.generation.poet.FormatSpecifier.asCodeBlock
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationContext
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationSpiRegistry
import io.toolisticon.kotlin.generation.spi.KotlinCodeGenerationStrategy
import io.toolisticon.kotlin.generation.spi.registry.KotlinCodeGenerationServiceLoader
import io.toolisticon.kotlin.generation.spi.strategy.executeAll
import io.toolisticon.kotlin.generation.support.SUPPRESS_MEMBER_VISIBILITY_CAN_BE_PRIVATE
import mu.KLogging
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Kotlin Code Generation is a wrapper lib for kotlin poet. This is the central class that allows access to builders and tools via simple static helpers.
 */
@ExperimentalKotlinPoetApi
object KotlinCodeGeneration : KLogging() {

  /**
   * Build a [KotlinAnnotationSpec] using given type and receiver fn.
   * @see [KotlinAnnotationSpecBuilder.builder]
   */
  inline fun buildAnnotation(type: KClass<*>, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = buildAnnotation(type.asClassName(), block)

  /**
   * Build a [KotlinAnnotationSpec] using given className and receiver fn.
   * @see [KotlinAnnotationSpecBuilder.builder]
   */
  inline fun buildAnnotation(className: ClassName, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = annotationBuilder(className).also(block).build()

  /**
   * Build a [KotlinAnnotationClassSpec] using given className and receiver fn.
   * @see [KotlinAnnotationClassSpecBuilder.builder]
   */
  inline fun buildAnnotationClass(className: ClassName, block: KotlinAnnotationClassSpecBuilderReceiver = {}) = annotationClassBuilder(className).also(block).build()

  /**
   * Build a [KotlinAnnotationClassSpec] using given package- and simpleName and receiver fn.
   * @see [KotlinAnnotationClassSpecBuilder.builder]
   */
  @SuppressWarnings("unused")
  inline fun buildAnnotationClass(packageName: PackageName, simpleName: SimpleName, block: KotlinAnnotationClassSpecBuilderReceiver = {}) = buildAnnotationClass(className(packageName, simpleName), block)

  /**
   * Build a [KotlinAnonymousClassSpec] using given receiver fn.
   * @see [KotlinAnonymousClassSpecBuilder.builder]
   */
  inline fun buildAnonymousClass(block: KotlinAnonymousClassSpecBuilderReceiver = {}) = anonymousClassBuilder().also(block).build()

  /**
   * Build a [KotlinClassSpec] using given className and receiver fn.
   * @see [KotlinClassSpecBuilder.builder]
   */
  inline fun buildClass(className: ClassName, block: KotlinClassSpecBuilderReceiver = {}) = classBuilder(className).also(block).build()

  /**
   * Build a [KotlinClassSpec] using given package- and simpleName and receiver fn.
   * @see [KotlinClassSpecBuilder.builder]
   */
  inline fun buildClass(packageName: PackageName, simpleName: SimpleName, block: KotlinClassSpecBuilderReceiver = {}) = buildClass(className(packageName, simpleName), block)

  /**
   * @see [DelegateListValueClassSpecBuilder]
   */
  inline fun buildDelegateListValueClass(
    packageName: PackageName,
    simpleName: SimpleName,
    items: KClass<*>,
    block: DelegateListValueClassSpecBuilderReceiver = {}
  ) = buildDelegateListValueClass(className = className(packageName, simpleName), items = items.asTypeName(), block = block)

  /**
   * @see [DelegateListValueClassSpecBuilder]
   */
  inline fun buildDelegateListValueClass(
    packageName: PackageName,
    simpleName: SimpleName,
    items: TypeName,
    block: DelegateListValueClassSpecBuilderReceiver = {}
  ) = delegateListValueClassBuilder(
    className = className(packageName, simpleName),
    items = items
  ).also(block).build()


  /**
   * @see [DelegateListValueClassSpecBuilder]
   */
  inline fun buildDelegateListValueClass(
    className: ClassName,
    items: TypeName,
    block: DelegateListValueClassSpecBuilderReceiver = {}
  ) = delegateListValueClassBuilder(className, items).also(block).build()


  /**
   * @see [DelegateMapValueClassSpecBuilder]
   */
  inline fun buildDelegateMapValueClass(
    packageName: PackageName,
    simpleName: SimpleName,
    keyType: TypeName = DEFAULT_KEY_TYPE,
    valueType: TypeName,
    block: DelegateMapValueClassSpecBuilderReceiver = {}
  ) = buildDelegateMapValueClass(className(packageName, simpleName), keyType, valueType, block)


  /**
   * @see [DelegateMapValueClassSpecBuilder]
   */
  inline fun buildDelegateMapValueClass(
    className: ClassName,
    keyType: TypeName = DEFAULT_KEY_TYPE,
    valueType: TypeName,
    block: DelegateMapValueClassSpecBuilderReceiver = {}
  ) = delegateMapValueClassBuilder(className, keyType, valueType).also(block).build()

  /**
   * @see RuntimeExceptionSpecBuilder
   */
  inline fun buildRuntimeExceptionClass(packageName: PackageName, simpleName: SimpleName, block: RuntimeExceptionSpecBuilderReceiver = {}) = buildRuntimeExceptionClass(className(packageName, simpleName), block)

  /**
   * @see RuntimeExceptionSpecBuilder
   */
  inline fun buildRuntimeExceptionClass(className: ClassName, block: RuntimeExceptionSpecBuilderReceiver = {}) = runtimeExceptionClassBuilder(className).also(block).build()

  /**
   * @see [CodeBlock.of]
   */
  fun buildCodeBlock(format: CodeBlockFormat, vararg args: Any?) = CodeBlock.of(format, *args)

  /**
   * Build codeBlock using receiver-fn.
   * @see [CodeBlock.of]
   */
  @SuppressWarnings("unused")
  inline fun buildCodeBlock(block: CodeBlock.Builder.() -> Unit): CodeBlock = CodeBlock.builder().also(block).build()

  /**
   * Build [KotlinCompanionObjectSpec] using optional name and receiver fn.
   * @see [KotlinCompanionObjectSpecBuilder.builder]
   */
  inline fun buildCompanionObject(name: String? = null, block: KotlinCompanionObjectSpecBuilderReceiver = {}) = companionObjectBuilder(name).also(block).build()

  /**
   * Build [KotlinConstructorPropertySpec].
   * @see [KotlinConstructorPropertySpecBuilder.builder]
   */
  inline fun buildConstructorProperty(name: PropertyName, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = constructorPropertyBuilder(name, type).also(block).build()

  /**
   * Build [KotlinConstructorPropertySpec].
   * @see [KotlinConstructorPropertySpecBuilder.builder]
   */
  inline fun buildConstructorProperty(name: PropertyName, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = buildConstructorProperty(name, type.asTypeName(), block)

  /**
   * Build [KotlinDataClassSpec].
   * @see [KotlinDataClassSpecBuilder.builder]
   */
  inline fun buildDataClass(className: ClassName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec = KotlinDataClassSpecBuilder.builder(className).also(block).build()

  /**
   * Build [KotlinDataClassSpec].
   * @see [KotlinDataClassSpecBuilder.builder]
   */
  inline fun buildDataClass(packageName: PackageName, simpleName: SimpleName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec = buildDataClass(className(packageName, simpleName), block)

  /**
   * Build [KotlinEnumClassSpec].
   * @see [KotlinEnumClassSpecBuilder.builder]
   */
  inline fun buildEnumClass(className: ClassName, block: KotlinEnumClassSpecBuilderReceiver = {}) = KotlinEnumClassSpecBuilder.builder(className).also(block).build()

  /**
   * Build [KotlinEnumClassSpec].
   * @see [KotlinEnumClassSpecBuilder.builder]
   */
  @SuppressWarnings("unused")
  inline fun buildEnumClass(packageName: PackageName, simpleName: SimpleName, block: KotlinEnumClassSpecBuilderReceiver = {}) = buildEnumClass(className(packageName, simpleName), block)

  /**
   * Build [KotlinFileSpec].
   * @see [KotlinFileSpecBuilder.builder]
   */
  inline fun buildFile(className: ClassName, block: KotlinFileSpecBuilderReceiver = {}) = fileBuilder(className).also(block).build()

  /**
   * Build [KotlinFileSpec].
   * @see [KotlinFileSpecBuilder.builder]
   */
  @SuppressWarnings("unused")
  inline fun buildFile(packageName: PackageName, simpleName: SimpleName, block: KotlinFileSpecBuilderReceiver = {}): KotlinFileSpec = buildFile(className(packageName, simpleName), block)

  /**
   * Build [KotlinFunSpec].
   * @see [KotlinFunSpecBuilder.builder]
   */
  inline fun buildFun(name: FunctionName, block: KotlinFunSpecBuilderReceiver = {}): KotlinFunSpec = funBuilder(name).also(block).build()

  /**
   * Build [KotlinInterfaceSpec].
   * @see [KotlinInterfaceSpecBuilder.builder]
   */
  inline fun buildInterface(className: ClassName, block: KotlinInterfaceSpecBuilderReceiver = {}): KotlinInterfaceSpec = interfaceBuilder(className).also(block).build()

  /**
   * Build [KotlinInterfaceSpec].
   * @see [KotlinInterfaceSpecBuilder.builder]
   */
  @SuppressWarnings("unused")
  inline fun buildInterface(packageName: PackageName, simpleName: SimpleName, block: KotlinInterfaceSpecBuilderReceiver = {}): KotlinInterfaceSpec =
    buildInterface(className(packageName, simpleName), block)

  /**
   * Build [KotlinObjectSpec].
   * @see [KotlinObjectSpecBuilder.builder]
   */
  inline fun buildObject(className: ClassName, block: KotlinObjectSpecBuilderReceiver = {}): KotlinObjectSpec = objectBuilder(className).also(block).build()

  /**
   * Build [KotlinObjectSpec].
   * @see [KotlinObjectSpecBuilder.builder]
   */
  inline fun buildObject(packageName: PackageName, simpleName: SimpleName, block: KotlinObjectSpecBuilderReceiver = {}): KotlinObjectSpec = buildObject(className(packageName, simpleName), block)

  /**
   * Build [KotlinParameterSpec].
   * @see [KotlinParameterSpecBuilder.builder]
   */
  inline fun buildParameter(name: ParameterName, typeName: TypeName, block: KotlinParameterSpecBuilderReceiver = {}) = parameterBuilder(name, typeName).also(block).build()

  /**
   * Build [KotlinParameterSpec].
   * @see [KotlinParameterSpecBuilder.builder]
   */
  @SuppressWarnings("unused")
  inline fun buildParameter(name: ParameterName, type: KClass<*>, block: KotlinParameterSpecBuilderReceiver = {}): KotlinParameterSpec = buildParameter(name, type.asTypeName(), block)

  /**
   * Build [KotlinPropertySpec].
   * @see [KotlinPropertySpecBuilder.builder]
   */
  inline fun buildProperty(name: PropertyName, typeName: TypeName, block: KotlinPropertySpecBuilderReceiver = {}): KotlinPropertySpec = propertyBuilder(name, typeName).also(block).build()

  /**
   * Build [KotlinPropertySpec].
   * @see [KotlinPropertySpecBuilder.builder]
   */
  inline fun buildProperty(name: PropertyName, type: KClass<*>, block: KotlinPropertySpecBuilderReceiver = {}): KotlinPropertySpec = buildProperty(name, type.asTypeName(), block)

  /**
   * Build [KotlinTypeAliasSpec].
   * @see [KotlinTypeAliasSpecBuilder.builder]
   */
  inline fun buildTypeAlias(name: TypeAliasName, type: TypeName, block: KotlinTypeAliasSpecBuilderReceiver = {}): KotlinTypeAliasSpec = typeAliasBuilder(name, type).also(block).build()

  /**
   * Build [KotlinValueClassSpec].
   * @see [KotlinValueClassSpecBuilder.builder]
   */
  inline fun buildValueClass(className: ClassName, block: KotlinValueClassSpecBuilderReceiver = {}): KotlinValueClassSpec = valueClassBuilder(className).also(block).build()

  /**
   * Build [KotlinValueClassSpec].
   * @see [KotlinValueClassSpecBuilder.builder]
   */
  inline fun buildValueClass(packageName: PackageName, simpleName: SimpleName, block: KotlinValueClassSpecBuilderReceiver = {}): KotlinValueClassSpec =
    buildValueClass(className(packageName, simpleName), block)

  /**
   * Static access for all builders.
   */
  @ExperimentalKotlinPoetApi
  @Suppress("ClassName", SUPPRESS_MEMBER_VISIBILITY_CAN_BE_PRIVATE)
  object builder {
    /**
     * @see KotlinAnnotationClassSpecBuilder
     */
    fun annotationClassBuilder(className: ClassName) = KotlinAnnotationClassSpecBuilder.builder(className)

    /**
     * @see KotlinAnnotationClassSpecBuilder
     */
    @SuppressWarnings("unused")
    fun annotationClassBuilder(packageName: PackageName, simpleName: SimpleName) = annotationClassBuilder(className(packageName, simpleName))

    /**
     * @see KotlinAnnotationSpecBuilder
     */
    fun annotationBuilder(type: ClassName) = KotlinAnnotationSpecBuilder.builder(type)

    /**
     * @see KotlinAnnotationSpecBuilder
     */
    @SuppressWarnings("unused")
    fun annotationBuilder(type: KClass<out Annotation>) = annotationBuilder(type.asClassName())

    /**
     * @see KotlinAnnotationSpecBuilder
     */
    @SuppressWarnings("unused")
    fun annotationBuilder(packageName: PackageName, simpleName: SimpleName) = annotationBuilder(className(packageName, simpleName))

    /**
     * @see KotlinAnonymousClassSpecBuilder
     */
    fun anonymousClassBuilder() = KotlinAnonymousClassSpecBuilder.builder()

    /**
     * @see KotlinClassSpecBuilder
     */
    fun classBuilder(className: ClassName) = KotlinClassSpecBuilder.builder(className)

    /**
     * @see KotlinClassSpecBuilder
     */
    fun classBuilder(packageName: PackageName, simpleName: SimpleName) = classBuilder(className(packageName, simpleName))

    /**
     * @see KotlinCompanionObjectSpecBuilder
     */
    fun companionObjectBuilder(name: String? = null) = KotlinCompanionObjectSpecBuilder.builder(name)

    /**
     * @see KotlinConstructorPropertySpecBuilder
     */
    fun constructorPropertyBuilder(name: PropertyName, type: TypeName) = KotlinConstructorPropertySpecBuilder.builder(name, type)

    /**
     * @see KotlinFunSpecBuilder
     */
    fun constructorBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder.constructorBuilder()

    /**
     * @see KotlinDataClassSpecBuilder
     */
    fun dataClassBuilder(className: ClassName) = KotlinDataClassSpecBuilder.builder(className)

    /**
     * @see KotlinDataClassSpecBuilder
     */
    @SuppressWarnings("unused")
    fun dataClassBuilder(packageName: PackageName, simpleName: SimpleName) = dataClassBuilder(className(packageName, simpleName))

    /**
     * @see DelegateListValueClassSpecBuilder
     */
    fun delegateListValueClassBuilder(className: ClassName, items: TypeName) = DelegateListValueClassSpecBuilder.builder(className, items)

    /**
     * @see DelegateMapValueClassSpecBuilder
     */
    fun delegateMapValueClassBuilder(className: ClassName, keyType: TypeName = DEFAULT_KEY_TYPE, valueType: TypeName) = DelegateMapValueClassSpecBuilder.builder(className, keyType, valueType)

    /**
     * @see KotlinEnumClassSpecBuilder
     */
    @SuppressWarnings("unused")
    fun enumClassBuilder(name: SimpleName) = KotlinEnumClassSpecBuilder.builder(name)

    /**
     * @see KotlinEnumClassSpecBuilder
     */
    @SuppressWarnings("unused")
    fun enumClassBuilder(packageName: PackageName, name: SimpleName) = enumClassBuilder(className(packageName, name))

    /**
     * @see KotlinEnumClassSpecBuilder
     */
    fun enumClassBuilder(className: ClassName) = KotlinEnumClassSpecBuilder.builder(className)

    /**
     * @see RuntimeExceptionSpecBuilder
     */
    fun runtimeExceptionClassBuilder(className: ClassName) = RuntimeExceptionSpecBuilder.builder(className)

    /**
     * @see KotlinFileSpecBuilder
     */
    fun fileBuilder(className: ClassName) = KotlinFileSpecBuilder.builder(className)

    /**
     * @see KotlinFileSpecBuilder
     */
    fun fileBuilder(packageName: PackageName, simpleName: SimpleName) = fileBuilder(className(packageName, simpleName))

    /**
     * @see KotlinFunSpecBuilder
     */
    fun funBuilder(name: FunctionName) = KotlinFunSpecBuilder.builder(name)

    /**
     * @see KotlinFunSpecBuilder
     */
    fun getterBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder.getterBuilder()

    /**
     * @see KotlinInterfaceSpecBuilder
     */
    fun interfaceBuilder(className: ClassName) = KotlinInterfaceSpecBuilder.builder(className)

    /**
     * @see KotlinInterfaceSpecBuilder
     */
    fun interfaceBuilder(packageName: PackageName, simpleName: SimpleName) = interfaceBuilder(className(packageName, simpleName))

    /**
     * @see KotlinObjectSpecBuilder
     */
    fun objectBuilder(className: ClassName) = KotlinObjectSpecBuilder.builder(className)

    /**
     * @see KotlinObjectSpecBuilder
     */
    fun objectBuilder(packageName: PackageName, simpleName: SimpleName) = objectBuilder(className(packageName, simpleName))

    /**
     * @see KotlinParameterSpecBuilder
     */
    fun parameterBuilder(name: ParameterName, type: TypeName) = KotlinParameterSpecBuilder.builder(name, type)

    /**
     * @see KotlinParameterSpecBuilder
     */
    fun parameterBuilder(name: ParameterName, type: KClass<*>) = parameterBuilder(name, type.asTypeName())

    /**
     * @see KotlinPropertySpecBuilder
     */
    @SuppressWarnings("unused")
    fun propertyBuilder(name: PropertyName, type: TypeName) = KotlinPropertySpecBuilder.builder(name, type)

    /**
     * @see KotlinPropertySpecBuilder
     */
    @SuppressWarnings("unused")
    fun propertyBuilder(name: PropertyName, type: KClass<*>) = propertyBuilder(name, type.asTypeName())

    /**
     * @see KotlinFunSpecBuilder
     */
    fun setterBuilder() = KotlinFunSpecBuilder.setterBuilder()

    /**
     * @see KotlinTypeAliasSpecBuilder
     */
    fun typeAliasBuilder(name: TypeAliasName, type: TypeName) = KotlinTypeAliasSpecBuilder.builder(name, type)

    /**
     * @see KotlinTypeAliasSpecBuilder
     */
    fun typeAliasBuilder(name: String, type: KClass<*>) = KotlinTypeAliasSpecBuilder.builder(name, type)

    /**
     * @see KotlinValueClassSpecBuilder
     */
    fun valueClassBuilder(className: ClassName) = KotlinValueClassSpecBuilder.builder(className)

    /**
     * @see KotlinValueClassSpecBuilder
     */
    @SuppressWarnings("unused")
    fun valueClassBuilder(packageName: PackageName, simpleName: SimpleName) = valueClassBuilder(className(packageName, simpleName))
  }

  /**
   * Create [ClassName] for given package and simpleName.
   */
  fun className(packageName: PackageName, simpleName: SimpleName) = ClassName(packageName, simpleName)

  /**
   * Create [ClassName] with default packageName.
   */
  fun simpleClassName(simpleName: SimpleName) = className("", simpleName)

  /**
   * Static to spi.
   */
  @Suppress("ClassName")
  object spi {
    /**
     * The default classLoader supplier fn.
     */
    val defaultClassLoader: () -> ClassLoader = { Thread.currentThread().contextClassLoader }

    /**
     * Initializes registry using spi.
     */
    fun registry(
      contextTypeUpperBound: KClass<*> = Any::class,
      classLoader: ClassLoader = defaultClassLoader(),
      exclusions: Set<String> = emptySet()
    ): KotlinCodeGenerationSpiRegistry = KotlinCodeGenerationServiceLoader(contextTypeUpperBound = contextTypeUpperBound, classLoader = classLoader, exclusions = exclusions).invoke()
  }

  /**
   * TypeSpec helpers.
   */
  @Suppress("ClassName")
  object typeSpec {

    fun TypeSpec.hasModifier(modifier: KModifier) = this.modifiers.contains(modifier)

    val TypeSpec.isDataClass: Boolean get() = hasModifier(KModifier.DATA)
    val TypeSpec.isValueClass: Boolean get() = hasModifier(KModifier.VALUE)
  }

  @Suppress("ClassName")
  object name {
    fun Collection<MemberName>.asCodeBlock(): CodeBlock = this.map { it.asCodeBlock() }.joinToCode(prefix = "[", suffix = "]")

    fun Enum<*>.asMemberName(): MemberName = this::class.asClassName().member(this.name)

    fun TypeName.nullable(nullable: Boolean = true): TypeName = this.copy(nullable = nullable)
  }

  /**
   * Constants for kotlin-poet formats.
   */
  @Suppress("ClassName")
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

  /**
   * Generator Function that takes a context and an input, finds matching strategies and generates source file(s).
   *
   * Invokes the contextFactory and calls `generateFiles(context, input)`.
   *
   * @param INPUT the type of the input (base source of generation)
   * @param CONTEXT the context (containing registry, ...) used for generation.
   * @param input the instance of the input
   * @param contextFactory factory fn to create the context (containing the spi registry) used for generation from input
   * @return [KotlinFileSpecList] containing the generated files
   * @throws IllegalStateException when no matching strategy is found.
   */
  inline fun <reified CONTEXT : KotlinCodeGenerationContext<CONTEXT>, reified INPUT : Any> generateFiles(contextFactory: (INPUT) -> CONTEXT, input: INPUT): KotlinFileSpecList = generateFiles(
    context = contextFactory.invoke(input),
    input = input
  )

  /**
   * Generator Function that takes a context and an input, finds matching strategies and generates source file(s).
   *
   * @param INPUT the type of the input (base source of generation)
   * @param CONTEXT the context (containing registry, ...) used for generation.
   * @param input the instance of the input
   * @param context the context (containing the spi registry) used for generation
   * @return [KotlinFileSpecList] containing the generated files
   * @throws IllegalStateException when no matching strategy is found.
   */
  inline fun <reified CONTEXT : KotlinCodeGenerationContext<CONTEXT>, reified INPUT : Any> generateFiles(context: CONTEXT, input: INPUT): KotlinFileSpecList {
    val strategyCandidates = context.registry.strategies.filter { it.specType.isSubclassOf(KotlinFileSpecIterable::class) }
      .filter { it.contextType.isSubclassOf(CONTEXT::class) }
      .filter { it.inputType.isSubclassOf(INPUT::class) }
      .map {
        @Suppress("UNCHECKED_CAST")
        it as KotlinCodeGenerationStrategy<CONTEXT, INPUT, KotlinFileSpecIterable>
      }
    // find all matching strategies
    val matchingStrategies: List<KotlinCodeGenerationStrategy<CONTEXT, INPUT, KotlinFileSpecIterable>> = strategyCandidates.mapNotNull {
      if (it.test(context, input)) {
        it
      } else {
        logger.info { "strategy-filter: removing ${it.name}" }
        null
      }
    }.also {
      check(it.isNotEmpty()) { "No applicable strategy found/filtered for context=`${CONTEXT::class.simpleName}`, input=`${input::class.simpleName}`." }
    }

    // generate files
    val sourceFiles = matchingStrategies.executeAll(context, input).flatten().also {
      check(it.isNotEmpty()) { "No files where generated for context=`${CONTEXT::class.simpleName}`, input=`${input::class.simpleName}`." }
    }

    // wrap to spec list
    return KotlinFileSpecList(sourceFiles)
  }

}
