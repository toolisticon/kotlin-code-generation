package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.Annotatable.Builder
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.FunctionName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFun
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildProperty
import io.toolisticon.kotlin.generation.PropertyName
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.TypeSpecSupplier
import io.toolisticon.kotlin.generation.spec.*
import kotlin.reflect.KClass

/**
 * Core builder interface with self reference for fluent builder methods with inheritance.
 */
interface DelegatingBuilder<SELF, RECEIVER> {
  fun builder(block: RECEIVER): SELF
}

/**
 * Common interface for typeSpec builders.
 */
interface KotlinGeneratorTypeSpecBuilder<SELF, SPEC : KotlinGeneratorTypeSpec<SPEC>> : BuilderSupplier<SPEC, TypeSpec>,
  DelegatingBuilder<SELF, TypeSpecBuilderReceiver>,
  TypeSpecSupplier,
  KotlinGeneratorSpecSupplier<SPEC> {
  override fun spec(): SPEC = build()

  override fun get(): TypeSpec = spec().get()
}

/**
 * All typeSpecs that provide support for constructor properties use this shared code.
 */
@ExperimentalKotlinPoetApi
interface ConstructorPropertySupport<SELF> {

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
sealed interface KotlinAnnotatableBuilder<SELF> {

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
 * Typesafe wrapper for [com.squareup.kotlinpoet.Documentable.Builder]. Marks anything that can have `kdoc` documentation.
 *
 * * `addKdoc`
 */
@ExperimentalKotlinPoetApi
sealed interface KotlinDocumentableBuilder<SELF> {
  /**
   * Implementing builders have to add this to their build.
   */
  fun addKdoc(kdoc: KDoc): SELF

  /**
   * Wraps a codeBlock into a KDoc and adds it.
   * @see KotlinDocumentableBuilder.addKdoc
   */
  fun addKDoc(kdoc: CodeBlock): SELF = addKdoc(KDoc(kdoc))

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
sealed interface KotlinMemberSpecHolderBuilder<SELF> {
  fun addFunction(funSpec: KotlinFunSpecSupplier): SELF
  fun addFunction(name: FunctionName, block: KotlinFunSpecBuilderReceiver): SELF = addFunction(funSpec = buildFun(name, block))

  fun addProperty(propertySpec: KotlinPropertySpecSupplier): SELF
  fun addProperty(name: PropertyName, type: TypeName, block: KotlinPropertySpecBuilderReceiver): SELF = addProperty(propertySpec = buildProperty(name, type, block))
  fun addProperty(name: PropertyName, type: KClass<*>, block: KotlinPropertySpecBuilderReceiver): SELF = addProperty(propertySpec = buildProperty(name, type, block))

}

/**
 * Typesafe wrapper for [com.squareup.kotlinpoet.TypeSpecHolder.Builder]
 *
 * * `addType`
 */
@ExperimentalKotlinPoetApi
sealed interface KotlinTypeSpecHolderBuilder<SELF> {
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
