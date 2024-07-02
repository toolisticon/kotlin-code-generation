package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.*
import mu.KLogging
import kotlin.reflect.KClass

class KotlinDataClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val delegate: TypeSpecBuilder
) : BuilderSupplier<KotlinDataClassSpec, TypeSpec>,
  KotlinDataClassSpecSupplier,
  ConstructorPropertySupport<KotlinDataClassSpecBuilder>,
  DelegatingBuilder<KotlinDataClassSpecBuilder, TypeSpecBuilderReceiver> {
  companion object : KLogging() {

    @JvmStatic
    fun builder(name: String): KotlinDataClassSpecBuilder = KotlinDataClassSpecBuilder(
      className = ClassName("", name),
      delegate = TypeSpecBuilder.classBuilder(name)
    )

    @JvmStatic
    fun builder(className: ClassName): KotlinDataClassSpecBuilder = KotlinDataClassSpecBuilder(className)
  }

  internal constructor(className: ClassName) : this(
    className = className,
    delegate = TypeSpecBuilder.classBuilder(className)
  )

  init {
    delegate.addModifiers(KModifier.DATA)
  }

  private val constructorProperties = LinkedHashMap<String, KotlinConstructorPropertySpecSupplier>()

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinDataClassSpec {
    check(constructorProperties.isNotEmpty()) { "Data class must have at least one property." }

    delegate.primaryConstructorWithProperties(toList(constructorProperties.values))

    return KotlinDataClassSpec(className = className, spec = delegate.build())
  }


  override fun spec(): KotlinDataClassSpec = build()
  override fun get(): TypeSpec = build().get()

//
//  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
//    delegate.addType(typeSpecSupplier.get())
//  }
//

  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply {
    this.constructorProperties[spec.name] = spec
  }

  fun addAnnotation(annotation: KotlinAnnotationSpecSupplier) = apply {
    delegate.addAnnotation(annotation.get())
  }


//  override fun addAnnotation(annotation: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.KotlinDataClassSpecBuilder = apply {
//    fun addAnnotation(annotation: ClassName): _root_ide_package_.io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder = apply {
//      delegate.addAnnotation(annotation)
//    }
//
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


typealias KotlinDataClassSpecBuilderReceiver = KotlinDataClassSpecBuilder.() -> Unit
