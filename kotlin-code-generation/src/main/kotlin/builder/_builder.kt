package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinPoetSpec
import io.toolisticon.kotlin.generation.spec.KotlinPoetTypeSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier
import kotlin.reflect.KClass

/**
 * Self-Type implementation of the root Spec builder. This is needed, because: a) we want implementing builders
 * to return an instance of themselves, so we can type-safe and fluently build products.
 * And b) because unfortunately kotlin-poet does not provide common super-types for specs and builders.
 *
 * @param SELF concrete instance of this builder to return in implementing classes.
 * @param PRODUCT the [KotlinPoetSpec] type returned by this builder.
 * @param SPEC the kotlin poet spec the [PRODUCT] refers to.
 * @param BUILDER the specific kotlin poet builder used to modify [PRODUCT].
 * @param delegate provide an instance of the specific builder
 */
sealed class KotlinPoetSpecBuilder<
  SELF : KotlinPoetSpecBuilder<SELF, PRODUCT, SPEC, BUILDER>,
  PRODUCT : KotlinPoetSpec<SPEC>,
  SPEC : Any,
  BUILDER : Any
  >(
  protected val delegate: BUILDER
) {

  @Suppress("UNCHECKED_CAST")
  protected fun applySelf(block: SELF.() -> Unit): SELF = (this as SELF).apply {
    block()
  }

  /**
   * Directly invoke underlying builder to support all build-in features.
   */
  operator fun invoke(block: BUILDER.() -> Unit): SELF = applySelf {
    delegate.block()
  }

  abstract fun build(): PRODUCT
}

sealed class KotlinPoetTypeSpecBuilder<T : KotlinPoetTypeSpec>(
  delegate: TypeSpec.Builder
) : KotlinPoetSpecBuilder<KotlinPoetTypeSpecBuilder<T>, T, TypeSpec, TypeSpec.Builder>(
  delegate = delegate
), TypeSpecSupplier {

  fun addAnnotation(annotationSpec: AnnotationSpec) = applySelf {
    delegate.addAnnotation(annotationSpec)
  }

  fun addAnnotation(annotationSpec: KClass<*>) = applySelf {
    delegate.addAnnotation(annotationSpec)
  }

  override fun get(): TypeSpec = build().get()
}


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

  fun addAnnotation(annotationSpec: KotlinAnnotationSpec): SELF

  fun addAnnotation(annotationSpec: AnnotationSpec): SELF = addAnnotation(KotlinAnnotationSpec(annotationSpec))

  fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>): SELF

  fun addAnnotation(annotation: ClassName): SELF

  fun addAnnotation(annotation: KClass<*>): SELF

  fun removeAnnotation(annotation: KClass<*>): SELF

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
