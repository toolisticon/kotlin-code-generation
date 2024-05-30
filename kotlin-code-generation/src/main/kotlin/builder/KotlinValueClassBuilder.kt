package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.jvm.jvmInline
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorProperty
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinValueClassBuilder internal constructor(className: ClassName, delegate: TypeSpec.Builder) : KotlinPoetNamedTypeSpecBuilder<KotlinValueClassSpec>(
  className = className,
  delegate = delegate
), TypeSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinValueClassSpec, KotlinValueClassBuilder> {

    operator fun invoke(packageName: String, name: String, block: TypeSpecBuilderReceiver = {}): KotlinValueClassBuilder = invoke(
      className = ClassName(packageName, name),
      block = block
    )

    operator fun invoke(name: String, block: TypeSpecBuilderReceiver = {}): KotlinValueClassBuilder = invoke(
      packageName = "",
      name = name,
      block = block
    )

    operator fun invoke(className: ClassName, block: TypeSpecBuilderReceiver = {}): KotlinValueClassBuilder {
      val builder: KotlinValueClassBuilder = KotlinValueClassBuilder(
        className = className,
        delegate = TypeSpec.classBuilder(className)
      )

      return builder.invoke(block) as KotlinValueClassBuilder
    }

    override fun invoke(spec: KotlinValueClassSpec, kind: TypeSpec.Kind, name: String?): KotlinValueClassBuilder = TODO() // FIXME KotlinValueClassBuilder(spec.get().toBuilder())
  }

  init {
    invoke {
      addModifiers(KModifier.VALUE)
      jvmInline()
    }
  }

  lateinit var constructorProperty: KotlinConstructorProperty

  fun primaryConstructor(constructorProperty: ConstructorPropertySupplier) = apply {
    this.constructorProperty = constructorProperty.get()

    val constructor = FunSpec.constructorBuilder()
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
