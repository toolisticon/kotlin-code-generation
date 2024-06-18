package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinCompanionObjectSpec
import io.toolisticon.kotlin.generation.spec.KotlinCompanionObjectSpecSupplier


class KotlinCompanionObjectSpecBuilder internal constructor(
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinCompanionObjectSpec, TypeSpec>, KotlinCompanionObjectSpecSupplier, DelegatingBuilder<KotlinCompanionObjectSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {
    @JvmStatic
    @JvmOverloads
    fun companionObjectBuilder(name: String? = null): KotlinCompanionObjectSpecBuilder = KotlinCompanionObjectSpecBuilder(
      delegate = TypeSpecBuilder.companionObjectBuilder(name)
    )
  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinCompanionObjectSpec = KotlinCompanionObjectSpec(
    spec = delegate.build()
  )

  override fun spec(): KotlinCompanionObjectSpec = build()
  override fun get(): TypeSpec = build().get()

//  operator fun invoke(spec: KotlinDataClassSpec): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder =
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder(
//      className = spec.className,
//      delegate = spec.get().toBuilder()
//    )
//}
//
//private val constructorProperties = LinkedHashMap<String, ConstructorPropertySupplier>()
//
//operator fun invoke(block: TypeSpecBuilder.() -> Unit): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder = apply {
//  delegate.block()
//  override fun addKdoc(kdoc: CodeBlock) = apply {
//    delegate.addKdoc(kdoc)
//  }
//
//  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
//    delegate.addType(typeSpecSupplier.get())
//  }
//
//  fun addConstructorProperty(name: String, type: TypeName, block: _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinConstructorPropertyBuilder.() -> Unit = {}) = apply {
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties[name] = buildConstructorProperty(name, type, block)
//  }
//
//  fun addConstructorProperty(constructorProperty: ConstructorPropertySupplier) = apply {
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties[constructorProperty.name] = constructorProperty
//  }
//
//  /**
//   * Finalize a data class based on its primary constructor parameters.
//   *
//   * * adds primary constructor.
//   * * backs parameters with properties.
//   */
//  override fun build(): KotlinDataClassSpec {
//    check(_root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties.isNotEmpty()) { "Data class must have at least one property." }
//
//    val constructor = FunSpec.constructorBuilder()
//
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.constructorProperties.values.map(ConstructorPropertySupplier::get).forEach {
//      constructor.addParameter(it.parameter.get())
//      delegate.addProperty(it.property.get())
//    }
//
//    delegate.primaryConstructor(constructor.build())
//
//    return KotlinDataClassSpec(className = className, spec = delegate.build())
//  }
//
//  override fun addAnnotation(annotation: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder = apply {
//    fun addAnnotation(annotation: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder = apply {
//      delegate.addAnnotation(annotation)
//    }
//
//    override fun get(): TypeSpec = build().get()
//
//  }
}


//  @Suppress("ClassName")
//  object builder :
//    _root_ide_package_.io.toolisticon.kotlin.generation._BAK.ToKotlinPoetSpecBuilder<KotlinDataClassSpec, _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder> {
//
//    fun builder(className: ClassName) = _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder(className = className)
//    operator fun invoke(packageName: String, name: String): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder =
//      _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.builder.invoke(ClassName(packageName, name))
//
//    fun builder(packageName: String, name: String) =
//      _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder.Companion.builder.builder(ClassName(packageName, name))
//    operator fun invoke(className: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder =
//      _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder(
//        className = className,
//        delegate = TypeSpec.classBuilder(className)
//      )
//
//  }
