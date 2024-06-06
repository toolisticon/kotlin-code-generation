package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.spec.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.DataClassSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinDataClassSpecBuilder internal constructor(className: ClassName, delegate: TypeSpec.Builder) : KotlinPoetNamedTypeSpecBuilder<KotlinDataClassSpec>(
  className = className,
  delegate = delegate.addModifiers(KModifier.DATA)
), DataClassSpecSupplier, IDataClassSpecBuilder<KotlinDataClassSpecBuilder, KotlinDataClassSpec> {

  @Suppress("ClassName")
  object builder : ToKotlinPoetSpecBuilder<KotlinDataClassSpec, KotlinDataClassSpecBuilder> {

    operator fun invoke(packageName: String, name: String): KotlinDataClassSpecBuilder = invoke(ClassName(packageName, name))

    operator fun invoke(className: ClassName): KotlinDataClassSpecBuilder = KotlinDataClassSpecBuilder(
      className = className,
      delegate = TypeSpec.classBuilder(className)
    )

    override fun invoke(spec: KotlinDataClassSpec): KotlinDataClassSpecBuilder = KotlinDataClassSpecBuilder(
      className = spec.className,
      delegate = spec.get().toBuilder()
    )
  }

  private val constructorProperties = LinkedHashMap<String, ConstructorPropertySupplier>()

  override fun addKdoc(kdoc: CodeBlock) = apply {
    delegate.addKdoc(kdoc)
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun addConstructorProperty(constructorProperty: ConstructorPropertySupplier) = apply {
    constructorProperties.put(constructorProperty.name, constructorProperty)
  }

  /**
   * Finalize a data class based on its primary constructor parameters.
   *
   * * adds primary constructor.
   * * backs parameters with properties.
   */
  override fun build(): KotlinDataClassSpec {
    check(constructorProperties.isNotEmpty()) { "Data class must have at least one property." }

    val constructor = FunSpec.constructorBuilder()

    constructorProperties.values.map(ConstructorPropertySupplier::get).forEach {
      constructor.addParameter(it.parameter.get())
      delegate.addProperty(it.property.get())
    }

    delegate.primaryConstructor(constructor.build())

    return KotlinDataClassSpec(className = className, spec = delegate.build())
  }

  override fun addAnnotation(annotation: ClassName): KotlinDataClassSpecBuilder = apply {
    delegate.addAnnotation(annotation)
  }

}
