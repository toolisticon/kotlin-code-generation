package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.spec.*
import kotlin.reflect.KClass

class KotlinDataClassBuilder internal constructor(className: ClassName, delegate: TypeSpec.Builder) : KotlinPoetNamedTypeSpecBuilder<KotlinDataClassSpec>(
  className = className,
  delegate = delegate.addModifiers(KModifier.DATA)
), DataClassSpecSupplier {

  @Suppress("ClassName")
  object builder : ToKotlinPoetSpecBuilder<KotlinDataClassSpec, KotlinDataClassBuilder> {
    operator fun invoke(className: ClassName): KotlinDataClassBuilder = KotlinDataClassBuilder(
      className = className,
      delegate = TypeSpec.classBuilder(className)
    )

    override fun invoke(spec: KotlinDataClassSpec): KotlinDataClassBuilder = KotlinDataClassBuilder(
      className = spec.className,
      delegate = spec.get().toBuilder()
    )
  }

  private val parameterSpecs = mutableListOf<ParameterSpecSupplier>()

  fun addKdoc(kdoc: CodeBlock) = apply {
    delegate.addKdoc(kdoc)
  }

  fun addType(typeSpecSupplier: TypeSpecSupplier) = apply {
    delegate.addType(typeSpecSupplier.get())
  }

  fun parameter(parameterSpec: ParameterSpecSupplier): KotlinDataClassBuilder = apply {
    this.parameterSpecs.add(parameterSpec)
  }

  fun parameter(name: String, type: TypeName): KotlinDataClassBuilder = apply {
    this.parameterSpecs.add(KotlinParameterBuilder.builder(name, type))
  }


  /**
   * Finalize a data class based on its primary constructor parameters.
   *
   * * adds primary constructor.
   * * backs parameters with properties.
   */
  override fun build(): KotlinDataClassSpec {
    check(parameterSpecs.isNotEmpty())

    val parameters = parameterSpecs.map(ParameterSpecSupplier::get)

    val constructor = FunSpec.constructorBuilder()
      .addParameters(parameters)
      .build()
    val properties = parameters.map {
      PropertySpec.builder(it.name, it.type)
        .initializer("%N", it)
        .build()
    }

    delegate.primaryConstructor(constructor)
      .addProperties(properties)

    return KotlinDataClassSpec(className = className, spec = delegate.build())
  }

  fun addAnnotation(annotationSpec: KotlinAnnotationSpec): KotlinDataClassBuilder = apply {
    delegate.addAnnotation(annotationSpec.get())
  }

  fun addAnnotation(annotation: ClassName): KotlinDataClassBuilder = apply {
    delegate.addAnnotation(annotation)
  }


  fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>): KotlinDataClassBuilder = apply {
    annotationSpecs.forEach(::addAnnotation)
  }

  fun removeAnnotation(annotation: KClass<*>): KotlinDataClassBuilder = apply {
    TODO("Not yet implemented")
  }
}
