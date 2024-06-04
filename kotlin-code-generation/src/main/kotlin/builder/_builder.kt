package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget.FILE
import io.toolisticon.kotlin.generation.spec.*
import kotlin.reflect.KClass


fun interface Builder<T> {
  fun build(): T
}

sealed interface SpecBuilder<SELF : SpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>, PRODUCT : SpecSupplier<SPEC>, SPEC, SPEC_BUILDER> : Builder<PRODUCT>

interface AnnotatableSpecBuilder<SELF, PRODUCT : SpecSupplier<SPEC>, SPEC : Annotatable, SPEC_BUILDER : Annotatable.Builder<SPEC_BUILDER>> : Builder<PRODUCT>

interface DocumentableSpecBuilder<
  SELF : SpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>,
  PRODUCT : SpecSupplier<SPEC>,
  SPEC : Documentable,
  SPEC_BUILDER : Documentable.Builder<SPEC_BUILDER>> : SpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>

/**
 * Self-Type implementation of the root Spec builder. This is needed, because: a) we want implementing builders
 * to return an instance of themselves, so we can type-safe and fluently build products.
 * And b) because unfortunately kotlin-poet does not provide common super-types for specs and builders.
 *
 * @param SELF concrete instance of this builder to return in implementing classes.
 * @param PRODUCT the [KotlinPoetSpec] type returned by this builder.
 * @param SPEC the kotlin poet spec the [PRODUCT] refers to.
 * @param SPEC_BUILDER the specific kotlin poet builder used to modify [PRODUCT].
 * @param delegate provide an instance of the specific builder
 */
sealed class KotlinPoetSpecBuilder<
  SELF : KotlinPoetSpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>,
  PRODUCT : KotlinPoetSpec<SPEC>,
  SPEC : Any,
  SPEC_BUILDER : Any
  >(
  protected val delegate: SPEC_BUILDER
) : SpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER> {

  @Suppress("UNCHECKED_CAST")
  protected fun applySelf(block: SELF.() -> Unit): SELF = (this as SELF).apply {
    block()
  }

  /**
   * Directly invoke underlying builder to support all build-in features.
   */
  operator fun invoke(block: SPEC_BUILDER.() -> Unit): SELF = applySelf {
    delegate.block()
  }

  abstract override fun build(): PRODUCT
}

sealed class KotlinPoetTypeSpecBuilder<T : KotlinPoetTypeSpec>(
  delegate: TypeSpec.Builder
) : KotlinPoetSpecBuilder<KotlinPoetTypeSpecBuilder<T>, T, TypeSpec, TypeSpec.Builder>(
  delegate = delegate
), TypeSpecSupplier
//  AnnotatableSpecBuilder<KotlinPoetSpecBuilder<KotlinPoetTypeSpecBuilder<T>, T, TypeSpec, TypeSpec.Builder>, T, TypeSpec, TypeSpec.Builder>,
//, DocumentableSpecBuilder<KotlinPoetSpecBuilder<KotlinPoetTypeSpecBuilder<T>, T, TypeSpec, TypeSpec.Builder>, T, TypeSpec, TypeSpec.Builder>
{


  fun addAnnotation(annotationSpec: AnnotationSpec) = applySelf {
    delegate.addAnnotation(annotationSpec)
  }

  fun addKdoc(block: CodeBlock) = applySelf {
    delegate.addKdoc(block)
  }

  override fun get(): TypeSpec = build().get()
}


sealed class KotlinPoetNamedTypeSpecBuilder<T : KotlinPoetNamedTypeSpec>(
  protected val className: ClassName,
  delegate: TypeSpec.Builder
) : KotlinPoetTypeSpecBuilder<T>(
  delegate = delegate
), TypeSpecSupplier

typealias AnnotationSpecBuilderReceiver = AnnotationSpec.Builder.() -> Unit
typealias CodeBlockBuilderReceiver = CodeBlock.Builder.() -> Unit
typealias FileSpecBuilderReceiver = FileSpec.Builder.() -> Unit
typealias FunSpecBuilderReceiver = FunSpec.Builder.() -> Unit
typealias ParameterSpecBuilderReceiver = ParameterSpec.Builder.() -> Unit
typealias PropertySpecBuilderReceiver = PropertySpec.Builder.() -> Unit
typealias TypeAliasSpecBuilderReceiver = TypeAliasSpec.Builder.() -> Unit
typealias TypeSpecBuilderReceiver = TypeSpec.Builder.() -> Unit

interface ToKotlinPoetSpecBuilder<SPEC, BUILDER> {
  operator fun invoke(spec: SPEC): BUILDER
}

interface ToKotlinPoetTypeSpecBuilder<SPEC : KotlinPoetTypeSpec, BUILDER> {

  operator fun invoke(spec: SPEC, kind: TypeSpec.Kind = spec.get().kind, name: String? = spec.get().name): BUILDER
}

interface KotlinAnnotatableBuilder<SELF> {

  fun addAnnotation(annotationSpec: AnnotationSpec): SELF

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier): SELF = addAnnotation(annotationSpec.get())

  fun addAnnotation(annotation: ClassName): SELF = addAnnotation(AnnotationSpec.builder(annotation).build())

  fun addAnnotation(annotation: KClass<out Annotation>): SELF = addAnnotation(annotation.asClassName())

  //fun removeAnnotation(annotation: KClass<*>): SELF

//  fun <T : Annotation> TypeSpec.Builder.removeAnnotation(annotation: KClass<T>) = apply {
//    this.annotations.removeIf {
//      it.typeName == annotation.asClassName()
//    }
//  }
//
//  fun <T : Annotation> ParameterSpec.Builder.removeAnnotation(annotation: KClass<T>) = apply {
//    this.annotations.removeIf {
//      it.typeName == annotation.asClassName()
//    }
//  }

}


//sealed class KotlinPoetSpecBuilder<
//  SELF : KotlinPoetSpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>,
//  PRODUCT : KotlinPoetSpec<SPEC>,
//  SPEC : Any,
//  SPEC_BUILDER : Any
//  >(
//  protected val delegate: SPEC_BUILDER
//) : Builder<PRODUCT> {

//KotlinPoetSpecBuilder<KotlinAnnotationBuilder, KotlinAnnotationSpec, AnnotationSpec, Builder>
//class KotlinParameterBuilder internal constructor(delegate: ParameterSpec.Builder) : KotlinPoetSpecBuilder<KotlinParameterBuilder, KotlinParameterSpec, ParameterSpec, ParameterSpec.Builder>(
//interface KDBuilder: KotlinPoetSpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>, PRODUCT, SPEC, SPEC_BUILDER> {}

//SELF : KotlinPoetSpecBuilder<SELF, PRODUCT, SPEC, SPEC_BUILDER>
//KotlinPoetSpecBuilder<KotlinTypeAliasBuilder, KotlinTypeAliasSpec, TypeAliasSpec, TypeAliasSpec.Builder>
interface KotlinDocumentableBuilder<SELF> {

  fun addKdoc(format: String, vararg args: Any): SELF = addKdoc(CodeBlock.of(format, *args))

  fun addKdoc(block: CodeBlock): SELF
}
