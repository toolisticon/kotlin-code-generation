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

sealed interface AnnotatableBuilder<S : Annotatable, B : Annotatable.Builder<B>> : KotlinPoetBuilderSupplier<S, B>,
  Annotatable.Builder<AnnotatableBuilder<S, B>> {
  override val annotations: MutableList<AnnotationSpec> get() = get().annotations
}

@ExperimentalKotlinPoetApi
sealed interface ContextReceivableBuilder<S : ContextReceivable, B : ContextReceivable.Builder<B>> : KotlinPoetBuilderSupplier<S, B>,
  ContextReceivable.Builder<ContextReceivableBuilder<S, B>> {
  override val contextReceiverTypes: MutableList<TypeName> get() = get().contextReceiverTypes
}

sealed interface DocumentableBuilder<S : Documentable, B : Documentable.Builder<B>> : KotlinPoetBuilderSupplier<S, B>,
  Documentable.Builder<DocumentableBuilder<S, B>> {
  override val kdoc: CodeBlock.Builder get() = get().kdoc
}

sealed interface MemberSpecHolderBuilder<S : MemberSpecHolder, B : MemberSpecHolder.Builder<B>> : KotlinPoetBuilderSupplier<S, B>,
  MemberSpecHolder.Builder<MemberSpecHolderBuilder<S, B>> {
  override fun addProperty(propertySpec: PropertySpec): MemberSpecHolderBuilder<S, B>
  override fun addFunction(funSpec: FunSpec): MemberSpecHolderBuilder<S, B>
}

sealed interface OriginatingElementsHolderBuilder<S : OriginatingElementsHolder, B : OriginatingElementsHolder.Builder<B>> : KotlinPoetBuilderSupplier<S, B>,
  OriginatingElementsHolder.Builder<OriginatingElementsHolderBuilder<S, B>> {
  override val originatingElements: MutableList<Element> get() = get().originatingElements
}

sealed interface TaggableBuilder<S : Taggable, B : Taggable.Builder<B>> : KotlinPoetBuilderSupplier<S, B>,
  Taggable.Builder<TaggableBuilder<S, B>> {
  override val tags: MutableMap<KClass<*>, Any> get() = get().tags
}

sealed interface TypeSpecHolderBuilder<S : TypeSpecHolder, B : TypeSpecHolder.Builder<B>> : KotlinPoetBuilderSupplier<S, B>,
  TypeSpecHolder.Builder<TypeSpecHolderBuilder<S, B>> {
  override fun addType(typeSpec: TypeSpec): TypeSpecHolderBuilder<S, B>
}
