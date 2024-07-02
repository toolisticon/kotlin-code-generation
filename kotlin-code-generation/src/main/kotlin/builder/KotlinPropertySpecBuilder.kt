package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilder
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KotlinPropertySpecBuilder internal constructor(
  private val delegate: PropertySpecBuilder
) : BuilderSupplier<KotlinPropertySpec, PropertySpec>,
  KotlinPropertySpecSupplier,
  DelegatingBuilder<KotlinPropertySpecBuilder, PropertySpecBuilderReceiver> {

  companion object {

    @JvmStatic
    fun builder(
      name: String,
      type: TypeName,
      vararg modifiers: KModifier,
    ): KotlinPropertySpecBuilder = KotlinPropertySpecBuilder(
      delegate = PropertySpecBuilder.builder(name, type, *modifiers)
    )

    @JvmStatic
    fun builder(
      name: String,
      type: Type,
      vararg modifiers: KModifier
    ): KotlinPropertySpecBuilder = builder(
      name = name,
      type = type.asTypeName(),
      modifiers = modifiers
    )

    @JvmStatic
    fun builder(
      name: String,
      type: KClass<*>,
      vararg modifiers: KModifier
    ): KotlinPropertySpecBuilder = builder(name, type.asTypeName(), *modifiers)

    @JvmStatic
    fun builder(
      name: String,
      type: TypeName,
      modifiers: Iterable<KModifier>
    ): KotlinPropertySpecBuilder = KotlinPropertySpecBuilder(
      delegate = PropertySpecBuilder.builder(name, type, modifiers)
    )

    @JvmStatic
    fun builder(
      name: String,
      type: KClass<*>,
      modifiers: Iterable<KModifier>
    ): KotlinPropertySpecBuilder = KotlinPropertySpecBuilder(
      delegate = PropertySpecBuilder.builder(name, type.asTypeName(), modifiers)
    )

    @JvmStatic
    fun builder(spec: KotlinPropertySpec) = builder(spec.get())

    @JvmStatic
    fun builder(spec: PropertySpec) = KotlinPropertySpecBuilder(delegate = spec.toBuilder().wrap())
  }

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier): KotlinPropertySpecBuilder = apply {
    delegate.addAnnotation(annotationSpec.get())
  }

  override fun builder(block: PropertySpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinPropertySpec {
    val spec = delegate.build()
    return KotlinPropertySpec(spec = spec)
  }

  override fun spec(): KotlinPropertySpec = build()
  override fun get(): PropertySpec = build().get()
}

typealias KotlinPropertySpecBuilderReceiver = KotlinPropertySpecBuilder.() -> Unit
