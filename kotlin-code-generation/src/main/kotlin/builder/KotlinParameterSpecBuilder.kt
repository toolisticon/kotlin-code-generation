package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilder
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpec
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpecSupplier
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Builder for [KotlinParameterSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinParameterSpecBuilder internal constructor(
  private val delegate: ParameterSpecBuilder
) : BuilderSupplier<KotlinParameterSpec, ParameterSpec>,
  DelegatingBuilder<KotlinParameterSpecBuilder, ParameterSpecBuilderReceiver>,
  KotlinAnnotatableBuilder<KotlinParameterSpecBuilder>,
  KotlinDocumentableBuilder<KotlinParameterSpecBuilder>,
  KotlinModifiableBuilder<KotlinParameterSpecBuilder>,
  KotlinParameterSpecSupplier {

  companion object {

    fun builder(name: String, type: TypeName, vararg modifiers: KModifier): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type, *modifiers)
    )

    fun builder(name: String, type: Type, vararg modifiers: KModifier): KotlinParameterSpecBuilder = builder(name, type.asTypeName(), *modifiers)

    fun builder(name: String, type: KClass<*>, vararg modifiers: KModifier): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type.asTypeName(), *modifiers)
    )

    fun builder(name: String, type: TypeName, modifiers: Iterable<KModifier>): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type, modifiers)
    )

    fun builder(name: String, type: Type, modifiers: Iterable<KModifier>): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type.asTypeName(), modifiers)
    )

    fun builder(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type.asTypeName(), modifiers)
    )

    fun builder(spec: KotlinParameterSpec) = builder(spec.get())

    fun builder(spec: ParameterSpec) = KotlinParameterSpecBuilder(delegate = spec.toBuilder().wrap())
  }

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun addKdoc(kdoc: KDoc)= apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun tag(type: KClass<*>, tag: Any?)= builder { this.tag(type, tag) }

  fun defaultValue(format: String, vararg args: Any?) = builder { this.defaultValue(format, *args) }
  fun defaultValue(codeBlock: CodeBlock?) = builder { this.defaultValue(codeBlock) }

  override fun builder(block: ParameterSpecBuilderReceiver) = apply { delegate.builder.block() }
  override fun build(): KotlinParameterSpec = KotlinParameterSpec(spec = delegate.build())
  override fun spec(): KotlinParameterSpec = build()
  override fun get(): ParameterSpec = build().get()
}

@ExperimentalKotlinPoetApi
typealias KotlinParameterSpecBuilderReceiver = KotlinParameterSpecBuilder.() -> Unit
