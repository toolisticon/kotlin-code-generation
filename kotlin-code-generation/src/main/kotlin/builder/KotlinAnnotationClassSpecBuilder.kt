package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.toList

class KotlinAnnotationClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder,
  private val constructorProperties: LinkedHashMap<String, KotlinConstructorPropertySpecSupplier> = LinkedHashMap<String, KotlinConstructorPropertySpecSupplier>()
) : BuilderSupplier<KotlinAnnotationClassSpec, TypeSpec>, KotlinAnnotationClassSpecSupplier,
  ConstructorPropertySupport<KotlinAnnotationClassSpecBuilder>,
  DelegatingBuilder<KotlinAnnotationClassSpecBuilder, TypeSpecBuilderReceiver> {

  companion object {

    @JvmStatic
    fun builder(name: String): KotlinAnnotationClassSpecBuilder = builder(ClassName("", name))

    @JvmStatic
    fun builder(className: ClassName): KotlinAnnotationClassSpecBuilder = KotlinAnnotationClassSpecBuilder(
      className = className,
      delegate = TypeSpecBuilder.annotationBuilder(className.simpleName)
    )
  }

  init {
    delegate.addModifiers(KModifier.ANNOTATION)
  }

  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply {
    constructorProperties[spec.name] = spec
  }

  fun mustBeDocumented() = apply {
    delegate.addAnnotation(KotlinCodeGeneration.buildAnnotation(MustBeDocumented::class).get())
  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinAnnotationClassSpec {
    if (constructorProperties.isNotEmpty()) {
      delegate.primaryConstructorWithProperties(toList(constructorProperties.values))
    }

    return KotlinAnnotationClassSpec(className = className, spec = delegate.build())
  }

  override fun spec(): KotlinAnnotationClassSpec = build()

  override fun get(): TypeSpec = build().get()
}

typealias KotlinAnnotationClassSpecBuilderReceiver = KotlinAnnotationClassSpecBuilder.() -> Unit
