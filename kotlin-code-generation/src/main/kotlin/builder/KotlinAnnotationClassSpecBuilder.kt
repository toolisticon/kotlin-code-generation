package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.*
import javax.lang.model.element.Element

class KotlinAnnotationClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinAnnotationClassSpecBuilder, KotlinAnnotationClassSpec>,
  ConstructorPropertySupport<KotlinAnnotationClassSpecBuilder>,
  KotlinDocumentableBuilder<KotlinAnnotationClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinAnnotationClassSpecBuilder> {

  companion object {

    fun builder(name: String): KotlinAnnotationClassSpecBuilder = builder(ClassName("", name))

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

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }

  override fun addKdoc(kdoc: KDoc): KotlinAnnotationClassSpecBuilder = apply {
    delegate.addKdoc(kdoc.get())
  }

  fun contextReceivers(vararg receiverTypes: TypeName): KotlinAnnotationClassSpecBuilder = builder { this.contextReceivers(*receiverTypes) }

  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinAnnotationClassSpecBuilder = apply { delegate.addFunction(funSpec.get()) }

  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinAnnotationClassSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }

  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }
  fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

  fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }

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

}

typealias KotlinAnnotationClassSpecBuilderReceiver = KotlinAnnotationClassSpecBuilder.() -> Unit
