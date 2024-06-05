package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.spec.ConstructorPropertySupplier
import io.toolisticon.kotlin.generation.spec.DataClassSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier
import mu.KLogging

class KotlinDataClassBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : Builder<KotlinDataClassSpec>, DataClassSpecSupplier {

  companion object : KLogging() {


    fun builder(className: ClassName) = KotlinDataClassBuilder(className = className)

    fun builder(packageName: String, name: String) = builder(ClassName(packageName, name))

  }

  internal constructor(className: ClassName) : this(className = className, delegate = TypeSpecBuilder.classBuilder(className))

  init {
      delegate.get().addModifiers(KModifier.DATA)
  }

  private val constructorProperties = LinkedHashMap<String, ConstructorPropertySupplier>()

  operator fun invoke(block: TypeSpecBuilder.() -> Unit): KotlinDataClassBuilder = apply {
    delegate.block()
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun addConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertyBuilder.() -> Unit = {}) = apply {
    constructorProperties[name] = buildConstructorProperty(name, type, block)
  }

  fun addConstructorProperty(constructorProperty: ConstructorPropertySupplier) = apply {
    constructorProperties[constructorProperty.name] = constructorProperty
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

  fun addAnnotation(annotation: ClassName): KotlinDataClassBuilder = apply {
    delegate.addAnnotation(annotation)
  }

  override fun get(): TypeSpec = build().get()

}
