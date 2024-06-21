package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.jvm.jvmInline
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpec
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpecSupplier

class KotlinValueClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinValueClassSpec, TypeSpec>, KotlinValueClassSpecSupplier, DelegatingBuilder<KotlinValueClassSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {

    @JvmStatic
    fun builder(name: String): KotlinValueClassSpecBuilder = KotlinValueClassSpecBuilder(
      className = ClassName("", name),
      delegate = TypeSpecBuilder.classBuilder(name)
    )

    @JvmStatic
    fun builder(className: ClassName): KotlinValueClassSpecBuilder = KotlinValueClassSpecBuilder(className)
  }

  lateinit var constructorProperty: KotlinConstructorPropertySpec


  internal constructor(className: ClassName) : this(
    className = className,
    delegate = TypeSpecBuilder.classBuilder(className)
  )

  init {
    delegate.addModifiers(KModifier.VALUE)
    delegate.builder.jvmInline()
  }

  fun primaryConstructor(constructorPropertySupplier: KotlinConstructorPropertySpecSupplier) = apply {
    this.constructorProperty = constructorPropertySupplier.spec()

    val constructor = KotlinFunSpecBuilder.constructorBuilder()
      .addParameter(this.constructorProperty.parameter)
      .build()

    delegate.addProperty(constructorProperty.property.get())
    delegate.builder.primaryConstructor(constructor.get())
  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinValueClassSpec {
    check(::constructorProperty.isInitialized) { "Value class must have exactly one property." }

    val spec = delegate.build()
    return KotlinValueClassSpec(className = className, spec = spec)
  }

  override fun spec(): KotlinValueClassSpec = build()
  override fun get(): TypeSpec = build().get()
}

typealias KotlinValueClassSpecBuilderReceiver = KotlinValueClassSpecBuilder.() -> Unit

