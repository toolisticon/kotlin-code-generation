package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinClassSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier


class KotlinClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinClassSpec, TypeSpec>,
  KotlinClassSpecSupplier,
  DelegatingBuilder<KotlinClassSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {
    @JvmStatic
    fun builder(name: String): KotlinClassSpecBuilder = KotlinClassSpecBuilder(
      className = ClassName("", name),
      delegate = TypeSpecBuilder.classBuilder(name)
    )

    @JvmStatic
    fun builder(className: ClassName): KotlinClassSpecBuilder = KotlinClassSpecBuilder(
      className = className,
      delegate = TypeSpecBuilder.classBuilder(className.simpleName)
    )
  }

  fun addKdoc(format: String, vararg args: Any) = builder { addKdoc(format, *args) }
  fun addKdoc(block: CodeBlock) = builder { addKdoc(block) }

  fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = builder {
    delegate.addAnnotation(spec.get())
  }

  fun addFunction(funSpec : KotlinFunSpecSupplier) = apply {
    delegate.addFunction(funSpec.get())
  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinClassSpec = KotlinClassSpec(
    className = className,
    spec = delegate.build()
  )

  override fun spec(): KotlinClassSpec = build()
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

typealias KotlinClassSpecBuilderReceiver = KotlinClassSpecBuilder.() -> Unit
