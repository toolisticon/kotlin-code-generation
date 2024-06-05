package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.jvm.jvmInline
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.spec.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorProperty
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec

class KotlinValueClassBuilder(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder,
) : Builder<KotlinValueClassSpec> {

  companion object {
    private fun TypeSpecBuilder.init() = apply {
      get().addModifiers(KModifier.VALUE)
      get().jvmInline()
    }

    fun builder(className: ClassName): KotlinValueClassBuilder = KotlinValueClassBuilder(
      className = className,
      delegate = TypeSpecBuilder.classBuilder(className).init()
    )
  }

  lateinit var constructorProperty: KotlinConstructorProperty

  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinValueClassBuilder = apply {
    delegate.block()
  }

  fun primaryConstructor(constructorProperty: ConstructorPropertySupplier) = apply {
    this.constructorProperty = constructorProperty.get()

    val constructor = FunSpecBuilder.constructorBuilder()
      .addParameter(this.constructorProperty.parameter.get())
      .build()

    delegate.addProperty(this.constructorProperty.property.get())
      .primaryConstructor(constructor)
  }

  override fun build(): KotlinValueClassSpec {
    check(::constructorProperty.isInitialized) { "Value class must have exactly one property." }

    return KotlinValueClassSpec(
      className = className,
      spec = delegate.build()
    )
  }
}
