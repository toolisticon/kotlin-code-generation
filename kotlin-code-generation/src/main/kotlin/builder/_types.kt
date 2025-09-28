package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.FunctionName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFun
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildProperty
import io.toolisticon.kotlin.generation.PropertyName
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.poet.CodeBlockBuilder
import io.toolisticon.kotlin.generation.poet.CodeBlockBuilderReceiver
import io.toolisticon.kotlin.generation.poet.CodeBlockSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.PoetAddCodeBlockBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.*
import kotlin.reflect.KClass

/**
 * Core builder interface with self reference for fluent builder methods with inheritance.
 */
sealed interface DelegatingBuilder<SELF, RECEIVER> {
  fun builder(block: RECEIVER): SELF
}

/**
 * Common interface for typeSpec builders.
 */
interface KotlinGeneratorTypeSpecBuilder<SELF, SPEC : KotlinGeneratorTypeSpec<SPEC>> : BuilderSupplier<SPEC, TypeSpec>,
  DelegatingBuilder<SELF, TypeSpecBuilderReceiver>, TypeSpecSupplier,
  KotlinGeneratorSpecSupplier<SPEC> {

  override fun spec(): SPEC = build()
  override fun get(): TypeSpec = spec().get()
}

/**
 * All typeSpecs that provide support for constructor properties use this shared code.
 */
@ExperimentalKotlinPoetApi
interface KotlinConstructorPropertySupport<SELF> : KotlinTaggableBuilder<SELF> {

  /**
   * Implementing builder needs to store the spec provided and apply it to the build.
   */
  fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier): SELF

  /**
   * Allows inline declartion of constructor property.
   */
  fun addConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}): SELF = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )

  /**
   * Allows inline declartion of constructor property.
   */
  fun addConstructorProperty(name: String, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}): SELF = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )
}

/**
 * Typesafe wrapper for [Annotatable.Builder].
 */
@OptIn(ExperimentalKotlinPoetApi::class)
interface KotlinAnnotatableBuilder<SELF> : KotlinTaggableBuilder<SELF> {

  /**
   * Implementing builder needs to store the spec provided and apply it to the build.
   */
  fun addAnnotation(spec: KotlinAnnotationSpecSupplier): SELF

  /**
   * Add annotation.
   * @see `KotlinAnnotatableBuilder.addAnnotation(KotlinAnnotationSpecSupplier)`
   */
  fun addAnnotation(annotationSpec: AnnotationSpec): SELF = addAnnotation(KotlinAnnotationSpec(annotationSpec))

  /**
   * Add annotation.
   * @see `KotlinAnnotatableBuilder.addAnnotation(KotlinAnnotationSpecSupplier)`
   */
  fun addAnnotation(annotation: ClassName): SELF = addAnnotation(buildAnnotation(annotation))

  /**
   * Add annotation.
   * @see `KotlinAnnotatableBuilder.addAnnotation(KotlinAnnotationSpecSupplier)`
   */
  fun addAnnotation(annotation: KClass<*>): SELF = addAnnotation(annotation.asClassName())

  /**
   * Add annotation.
   * @see `KotlinAnnotatableBuilder.addAnnotation(KotlinAnnotationSpecSupplier)`
   */
  fun addAnnotation(annotationSpec: AnnotationSpecSupplier): SELF = addAnnotation(annotationSpec.get())
}

/**
 * Everything related to code blocks.
 */
interface KotlinAddCodeBlockBuilder<SELF> {

  /**
   * Adds a [CodeBlock]. Must be implemented by the concrete builder.
   */
  fun addCode(codeBlock: CodeBlock): SELF

  /**
   * Formats the given [String] and arguments as [CodeBlock] and adds it.
   */
  fun addCode(format: String, vararg args: Any?): SELF = addCode(CodeBlockBuilder.builder().add(format, *args).build())

  /**
   * Add a [CodeBlock] by providing a supplier.
   */
  fun addCode(supplier: CodeBlockSupplier): SELF = addCode(supplier.get())

  /**
   * Receiver function for codeblock, use `builder.addCode { ... }` to add code.
   */
  fun addCode(block: CodeBlockBuilderReceiver): SELF = addCode(
    CodeBlockBuilder.builder().also(block).build()
  )
}
/**
 * Typesafe wrapper for [com.squareup.kotlinpoet.Documentable.Builder]. Marks anything that can have `kdoc` documentation.
 *
 * * `addKdoc`
 */
@ExperimentalKotlinPoetApi
interface KotlinDocumentableBuilder<SELF> : KotlinTaggableBuilder<SELF> {
  /**
   * Implementing builders have to add this to their build.
   */
  fun addKdoc(kdoc: KDoc): SELF

  /**
   * Wraps a codeBlock into a KDoc and adds it.
   * @see KotlinDocumentableBuilder.addKdoc
   */
  fun addKdoc(kdoc: CodeBlock): SELF = addKdoc(KDoc(kdoc))

  /**
   * Wraps a single string and adds it.
   * @see KotlinDocumentableBuilder.addKdoc
   */
  fun addKdoc(docs: String): SELF = addKdoc(KDoc.of(docs))

  /**
   * Creates a codeBlock using format and args and then addsIt.
   * @see KotlinDocumentableBuilder.addKdoc
   */
  fun addKdoc(format: String, first: String, vararg other: Any): SELF = addKdoc(KDoc.of(format, first, *other))
}

/**
 * Typesafe wrapper for [com.squareup.kotlinpoet.MemberSpecHolder.Builder].
 *
 * * `addFunction`
 * * `addProperty`
 */
@ExperimentalKotlinPoetApi
sealed interface KotlinMemberSpecHolderBuilder<SELF> : KotlinTaggableBuilder<SELF> {
  fun addFunction(funSpec: KotlinFunSpecSupplier): SELF
  fun addFunction(name: FunctionName, block: KotlinFunSpecBuilderReceiver): SELF = addFunction(funSpec = buildFun(name, block))

  fun addProperty(propertySpec: KotlinPropertySpecSupplier): SELF
  fun addProperty(name: PropertyName, type: TypeName, block: KotlinPropertySpecBuilderReceiver): SELF = addProperty(propertySpec = buildProperty(name, type, block))
  fun addProperty(name: PropertyName, type: KClass<*>, block: KotlinPropertySpecBuilderReceiver): SELF = addProperty(propertySpec = buildProperty(name, type, block))
}

/**
 * Shared wrapper fo all builders that support `addModifiers`
 */
interface KotlinModifiableBuilder<SELF> : KotlinTaggableBuilder<SELF> {

  /**
   * Add modifiers.
   *
   * Implementing builders have to add this to their build.
   */
  fun addModifiers(vararg modifiers: KModifier): SELF

  /**
   * @see vararg KotlinModifiableBuilder.addModifiers(KModifier)
   */
  fun addModifiers(modifiers: Iterable<KModifier>): SELF = addModifiers(*(modifiers.toList().toTypedArray()))

  /**
   * Adds [KModifier#ABSTRACT].
   */
  fun makeAbstract(): SELF = addModifiers(KModifier.ABSTRACT)

  /**
   * Adds [KModifier#ACTUAL].
   */
  fun makeActual(): SELF = addModifiers(KModifier.ACTUAL)

  /**
   * Adds [KModifier#ANNOTATION].
   */
  fun makeAnnotation(): SELF = addModifiers(KModifier.ANNOTATION)

  /**
   * Adds [KModifier#COMPANION].
   */
  fun makeCompanion(): SELF = addModifiers(KModifier.COMPANION)

  /**
   * Adds [KModifier#CONST].
   */
  fun makeConst(): SELF = addModifiers(KModifier.CONST)

  /**
   * Adds [KModifier#CROSSINLINE].
   */
  fun makeCrossinline(): SELF = addModifiers(KModifier.CROSSINLINE)

  /**
   * Adds [KModifier#ENUM].
   */
  fun makeEnum(): SELF = addModifiers(KModifier.ENUM)

  /**
   * Adds [KModifier#EXPECT].
   */
  fun makeExpect(): SELF = addModifiers(KModifier.EXPECT)

  /**
   * Adds [KModifier#EXTERNAL].
   */
  fun makeExternal(): SELF = addModifiers(KModifier.EXTERNAL)

  /**
   * Adds [KModifier#FINAL].
   */
  fun makeFinal(): SELF = addModifiers(KModifier.FINAL)

  /**
   * Adds [KModifier#INFIX].
   */
  fun makeInfix(): SELF = addModifiers(KModifier.INFIX)

  /**
   * Adds [KModifier#INLINE].
   */
  fun makeInline(): SELF = addModifiers(KModifier.INLINE)

  /**
   * Adds [KModifier#INNER].
   */
  fun makeInner(): SELF = addModifiers(KModifier.INNER)

  /**
   * Adds [KModifier#INTERNAL].
   */
  fun makeInternal(): SELF = addModifiers(KModifier.INTERNAL)

  /**
   * Adds [KModifier#LATEINIT].
   */
  fun makeLateinit(): SELF = addModifiers(KModifier.LATEINIT)

  /**
   * Adds [KModifier#NOINLINE].
   */
  fun makeNoinline(): SELF = addModifiers(KModifier.NOINLINE)

  /**
   * Adds [KModifier#OPEN].
   */
  fun makeOpen(): SELF = addModifiers(KModifier.OPEN)

  /**
   * Adds [KModifier#OPERATOR].
   */
  fun makeOperator(): SELF = addModifiers(KModifier.OPERATOR)

  /**
   * Adds [KModifier#OUT].
   */
  fun makeOut(): SELF = addModifiers(KModifier.OUT)

  /**
   * Adds [KModifier#OVERRIDE].
   */
  fun makeOverride(): SELF = addModifiers(KModifier.OVERRIDE)

  /**
   * Adds [KModifier#PRIVATE].
   */
  fun makePrivate(): SELF = addModifiers(KModifier.PRIVATE)

  /**
   * Adds [KModifier#PROTECTED].
   */
  fun makeProtected(): SELF = addModifiers(KModifier.PROTECTED)

  /**
   * Adds [KModifier#PUBLIC].
   */
  fun makePublic(): SELF = addModifiers(KModifier.PUBLIC)

  /**
   * Adds [KModifier#SEALED].
   */
  fun makeSealed(): SELF = addModifiers(KModifier.SEALED)

  /**
   * Adds [KModifier#SUSPEND].
   */
  fun makeSuspend(): SELF = addModifiers(KModifier.SUSPEND)

  /**
   * Adds [KModifier#TAILREC].
   */
  fun makeTailrec(): SELF = addModifiers(KModifier.TAILREC)

  /**
   * Adds [KModifier#VARARG].
   */
  fun makeVararg(): SELF = addModifiers(KModifier.VARARG)

  /**
   * Adds [KModifier#REIFIED].
   */
  fun makeReified(): SELF = addModifiers(KModifier.REIFIED)
}

/**
 * ContextReceivable for type-safe builders.
 */
sealed interface KotlinContextReceivableBuilder<SELF> : KotlinTaggableBuilder<SELF> {

  /**
   * @see KotlinContextReceivableBuilder#contextReceivers
   */
  fun contextReceivers(receiverTypes: Iterable<TypeName>): SELF = contextReceivers(*(receiverTypes.toList().toTypedArray()))

  /**
   * @see com.squareup.kotlinpoet.ContextReceivable.Builder.contextReceivers
   */
  fun contextReceivers(vararg receiverTypes: TypeName): SELF
}

interface KotlinSuperInterfaceSupport<SELF> : KotlinTaggableBuilder<SELF> {

  fun addSuperinterface(superinterface: TypeName, constructorParameter: String): SELF
  fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock = CodeBlockBuilder.EMPTY_CODE_BLOCK): SELF

  fun addSuperinterface(superinterface: KClass<*>, delegate: CodeBlock = CodeBlockBuilder.EMPTY_CODE_BLOCK) = addSuperinterface(superinterface.asTypeName(), delegate)
  fun addSuperinterfaces(superinterfaces: Iterable<TypeName>) = apply { superinterfaces.forEach(::addSuperinterface) }
  fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String) = addSuperinterface(superinterface.asTypeName(), constructorParameterName)

}

/**
 * Typesafe wrapper for [com.squareup.kotlinpoet.TypeSpecHolder.Builder]
 *
 * * `addType`
 */
@ExperimentalKotlinPoetApi
sealed interface KotlinTypeSpecHolderBuilder<SELF> : KotlinTaggableBuilder<SELF> {
  /**
   * @see KotlinTypeSpecHolderBuilder.addType(TypeSpecSupplier)
   */
  fun addType(spec: KotlinAnnotationClassSpecSupplier): SELF = addType(spec as TypeSpecSupplier)

  /**
   * @see KotlinTypeSpecHolderBuilder.addType(TypeSpecSupplier)
   */
  fun addType(spec: KotlinClassSpecSupplier): SELF = addType(spec as TypeSpecSupplier)

  /**
   * @see KotlinTypeSpecHolderBuilder.addType(TypeSpecSupplier)
   */
  fun addType(spec: KotlinDataClassSpecSupplier): SELF = addType(spec as TypeSpecSupplier)

  /**
   * @see KotlinTypeSpecHolderBuilder.addType(TypeSpecSupplier)
   */
  fun addType(spec: KotlinEnumClassSpecSupplier): SELF = addType(spec as TypeSpecSupplier)

  /**
   * @see KotlinTypeSpecHolderBuilder.addType(TypeSpecSupplier)
   */
  fun addType(spec: KotlinInterfaceSpecSupplier): SELF = addType(spec as TypeSpecSupplier)

  /**
   * @see KotlinTypeSpecHolderBuilder.addType(TypeSpecSupplier)
   */
  fun addType(spec: KotlinObjectSpecSupplier): SELF = addType(spec as TypeSpecSupplier)

  /**
   * @see KotlinTypeSpecHolderBuilder.addType(TypeSpecSupplier)
   */
  fun addType(spec: KotlinValueClassSpecSupplier): SELF = addType(spec as TypeSpecSupplier)

  /**
   * Implementing builders must add this to their internal builder.
   */
  fun addType(typeSpec: TypeSpecSupplier): SELF
}

/**
 * Support tagging.
 * @see com.squareup.kotlinpoet.Taggable#Builder
 */
sealed interface KotlinTaggableBuilder<SELF> {
  /**
   * @see com.squareup.kotlinpoet.Taggable#Builder#tag
   */
  fun addTag(type: KClass<*>, tag: Any?): SELF

  /**
   * Store tag under key of ::class.
   */
  fun <T : Any> addTag(tag: T): SELF = addTag(tag::class, tag)

  fun removeTag(type: KClass<*>): SELF = addTag(type, null)
}

/**
 * Groups the features of [KotlinAnnotatableBuilder], [KotlinDocumentableBuilder] and [KotlinModifiableBuilder].
 */
@ExperimentalKotlinPoetApi
interface KotlinAnnotatableDocumentableModifiableBuilder<SELF> :
  KotlinAnnotatableBuilder<SELF>,
  KotlinDocumentableBuilder<SELF>,
  KotlinModifiableBuilder<SELF>
