package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpecSupplier
import kotlin.reflect.KClass

/**
 * Builder for [KotlinTypeAliasSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinTypeAliasSpecBuilder internal constructor(
  private val delegate: TypeAliasSpecBuilder
) : BuilderSupplier<KotlinTypeAliasSpec, TypeAliasSpec>,
  DelegatingBuilder<KotlinTypeAliasSpecBuilder, TypeAliasSpecBuilderReceiver>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinTypeAliasSpecBuilder>,
  KotlinTypeAliasSpecSupplier {

  companion object {

    /**
     * Creates new builder.
     */
    fun builder(name: String, type: TypeName): KotlinTypeAliasSpecBuilder = KotlinTypeAliasSpecBuilder(name, type)

    /**
     * Creates new builder.
     */
    fun builder(name: String, type: KClass<*>): KotlinTypeAliasSpecBuilder = builder(name, type.asTypeName())
  }

  internal constructor(name: String, type: TypeName) : this(delegate = TypeAliasSpecBuilder.builder(name, type))

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) = builder { this.addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }

  override fun build() = KotlinTypeAliasSpec(spec = delegate.build())

  // region [overrides]
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: TypeAliasSpecBuilderReceiver) = apply { delegate.builder.block() }
  override fun get(): TypeAliasSpec = build().get()
  override fun spec(): KotlinTypeAliasSpec = build()
  // endregion [overrides]
}

@ExperimentalKotlinPoetApi
typealias KotlinTypeAliasSpecBuilderReceiver = KotlinTypeAliasSpecBuilder.() -> Unit
