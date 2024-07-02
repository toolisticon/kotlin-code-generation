package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.toList

class KotlinAnnotationClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
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

  private lateinit var _retention: AnnotationRetention
  private val constructorProperties: LinkedHashMap<String, KotlinConstructorPropertySpecSupplier> = LinkedHashMap()
  private val targets: MutableSet<AnnotationTarget> = mutableSetOf()
  private var repeatable: Boolean = false
  private var mustBeDocumented: Boolean = false

  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply { constructorProperties[spec.name] = spec }

  fun mustBeDocumented() = apply { this.mustBeDocumented = true }
  fun repeatable() = apply { this.repeatable = true }
  fun target(vararg targets: AnnotationTarget) = apply { this.targets.addAll(targets) }

  fun retention(retention: AnnotationRetention) = apply {
    this._retention = retention
  }

  override fun builder(block: TypeSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinAnnotationClassSpec {
    if (constructorProperties.isNotEmpty()) {
      delegate.primaryConstructorWithProperties(toList(constructorProperties.values))
    }
    if (targets.isNotEmpty()) {
      delegate.addAnnotation(buildAnnotation(Target::class) {
        addEnumMembers("allowedTargets", *(targets.sortedBy(AnnotationTarget::name).toTypedArray()))
      })
    }
    if (this::_retention.isInitialized) {
      delegate.addAnnotation(buildAnnotation(Retention::class) {
        addEnumMember("value", _retention)
      })
    }

    if (repeatable) {
      delegate.addAnnotation(Repeatable::class)
    }
    if (mustBeDocumented) {
      delegate.addAnnotation(MustBeDocumented::class)
    }

    return KotlinAnnotationClassSpec(className = className, spec = delegate.build())
  }

  override fun spec(): KotlinAnnotationClassSpec = build()

  override fun get(): TypeSpec = build().get()
}

typealias KotlinAnnotationClassSpecBuilderReceiver = KotlinAnnotationClassSpecBuilder.() -> Unit
