package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.poet.FunSpecSupplier
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilder
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.PropertySpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpec
import io.toolisticon.kotlin.generation.spec.KotlinPropertySpecSupplier
import java.lang.reflect.Type
import javax.lang.model.element.Element
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

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }
  fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }

  fun addKdoc(format: String, vararg args: Any) = builder { addKdoc(format, *args) }
  fun addKdoc(block: CodeBlock) = builder { addKdoc(block) }
  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }

  fun mutable(mutable: Boolean = true) = builder { this.mutable(mutable) }
  fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>) = builder { this.addModifiers(modifiers) }
  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) = builder { this.addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }
  fun initializer(format: String, vararg args: Any?) = builder { this.initializer(format, *args) }
  fun initializer(codeBlock: CodeBlock?) = builder { this.initializer(codeBlock) }
  fun delegate(format: String, vararg args: Any?) = builder { this.delegate(format, *args) }
  fun delegate(codeBlock: CodeBlock) = builder { this.delegate(codeBlock) }
  fun getter(getter: FunSpecSupplier?) = builder { this.getter(getter?.get()) }
  fun setter(setter: FunSpecSupplier?) = builder { this.setter(setter?.get()) }
  fun receiver(receiverType: TypeName?) = builder { this.receiver(receiverType) }
  fun receiver(receiverType: KClass<*>) = builder { this.receiver(receiverType) }

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
