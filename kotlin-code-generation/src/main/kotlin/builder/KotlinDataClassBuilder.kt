package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.DataClassSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier
import mu.KLogging

class KotlinDataClassBuilder internal constructor(className: ClassName, delegate: TypeSpec.Builder) : KotlinPoetNamedTypeSpecBuilder<KotlinDataClassSpec>(
  className = className,
  delegate = delegate.addModifiers(KModifier.DATA)
), DataClassSpecSupplier {

  companion object : KLogging() {

    fun builder(className: ClassName) = KotlinDataClassBuilder(className = className)

    fun builder(packageName: String, name: String) = builder(ClassName(packageName,name))

    fun from(spec: KotlinDataClassSpec, name : String = spec.className.simpleName) = KotlinDataClassBuilder(
      className = ClassName(packageName = spec.className.packageName, name),
      delegate = spec.get().toBuilder(name = name)
    )
  }

  internal constructor(className: ClassName) : this(className = className, delegate = TypeSpec.classBuilder(className))

  private val constructorProperties = LinkedHashMap<String, ConstructorPropertySupplier>()

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

  override fun addAnnotation(annotation: ClassName): KotlinDataClassBuilder = apply {
    delegate.addAnnotation(annotation)
  }

}
