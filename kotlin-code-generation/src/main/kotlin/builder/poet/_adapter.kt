package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.builder.Builder
import java.util.function.Supplier
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * @param S - kotlin poet spec (TypeSpec, FunSpec, ...)
 * @param B - kotlin poet spec specific builder (TypeSpec.Builder, ...)
 */
sealed interface KotlinPoetBuilderSupplier<S : Any, B : Any> : Builder<S>, Supplier<B>

sealed interface AnnotatableBuilder<SELF : AnnotatableBuilder<SELF, S, B>, S : Annotatable, B : Annotatable.Builder<B>>
  : KotlinPoetBuilderSupplier<S, B>, Annotatable.Builder<SELF> {
  override val annotations: MutableList<AnnotationSpec> get() = get().annotations

  fun addAnnotation(builder: AnnotationSpecBuilder): SELF = addAnnotation(builder.build())
}

@ExperimentalKotlinPoetApi
sealed interface ContextReceivableBuilder<SELF : ContextReceivableBuilder<SELF, S, B>, S : ContextReceivable, B : ContextReceivable.Builder<B>>
  : KotlinPoetBuilderSupplier<S, B>, ContextReceivable.Builder<SELF> {
  override val contextReceiverTypes: MutableList<TypeName> get() = get().contextReceiverTypes
}

sealed interface DocumentableBuilder<SELF : DocumentableBuilder<SELF, S, B>, S : Documentable, B : Documentable.Builder<B>>
  : KotlinPoetBuilderSupplier<S, B>, Documentable.Builder<SELF> {
  override val kdoc: CodeBlock.Builder get() = get().kdoc

  fun addKdoc(builder: CodeBlockBuilder) : SELF = addKdoc(builder.build())
}

sealed interface MemberSpecHolderBuilder<SELF : MemberSpecHolderBuilder<SELF, S, B>, S : MemberSpecHolder, B : MemberSpecHolder.Builder<B>>
  : KotlinPoetBuilderSupplier<S, B>, MemberSpecHolder.Builder<SELF> {
  override fun addProperty(propertySpec: PropertySpec): SELF

  @OptIn(ExperimentalKotlinPoetApi::class)
  fun addProperty(builder: PropertySpecBuilder): SELF = addProperty(builder.build())

  override fun addFunction(funSpec: FunSpec): SELF

  @OptIn(ExperimentalKotlinPoetApi::class)
  fun addFunction(builder: FunSpecBuilder): SELF = addFunction(builder.build())
}

sealed interface OriginatingElementsHolderBuilder<SELF : OriginatingElementsHolderBuilder<SELF, S, B>, S : OriginatingElementsHolder, B : OriginatingElementsHolder.Builder<B>>
  : KotlinPoetBuilderSupplier<S, B>, OriginatingElementsHolder.Builder<SELF> {
  override val originatingElements: MutableList<Element> get() = get().originatingElements
}

sealed interface TaggableBuilder<SELF : TaggableBuilder<SELF, S, B>, S : Taggable, B : Taggable.Builder<B>>
  : KotlinPoetBuilderSupplier<S, B>, Taggable.Builder<SELF> {
  override val tags: MutableMap<KClass<*>, Any> get() = get().tags
}

sealed interface TypeSpecHolderBuilder<SELF : TypeSpecHolderBuilder<SELF, S, B>, S : TypeSpecHolder, B : TypeSpecHolder.Builder<B>>
  : KotlinPoetBuilderSupplier<S, B>, TypeSpecHolder.Builder<SELF> {
  override fun addType(typeSpec: TypeSpec): SELF

  @OptIn(ExperimentalKotlinPoetApi::class)
  fun addType(builder: TypeSpecBuilder): SELF = addType(builder.build())
}
