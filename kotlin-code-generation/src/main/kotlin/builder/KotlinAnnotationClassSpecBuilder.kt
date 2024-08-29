package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertySpecBuilder.Companion.primaryConstructorWithProperties
import io.toolisticon.kotlin.generation.poet.*
import io.toolisticon.kotlin.generation.spec.*
import javax.lang.model.element.Element

/**
 * Builder for [KotlinAnnotationClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinAnnotationClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinAnnotationClassSpecBuilder, KotlinAnnotationClassSpec>,
  ConstructorPropertySupport<KotlinAnnotationClassSpecBuilder>,
  KotlinDocumentableBuilder<KotlinAnnotationClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinAnnotationClassSpecBuilder>,
  KotlinAnnotatableBuilder<KotlinAnnotationClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinAnnotationClassSpecBuilder> {

  companion object {

    fun builder(name: String): KotlinAnnotationClassSpecBuilder = builder(simpleClassName(name))

    fun builder(className: ClassName): KotlinAnnotationClassSpecBuilder = KotlinAnnotationClassSpecBuilder(className = className)
  }

  internal constructor(className: ClassName) : this(className, TypeSpecBuilder.annotationBuilder(className))

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

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier): KotlinAnnotationClassSpecBuilder = apply { delegate.addAnnotation(spec.get()) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier): KotlinAnnotationClassSpecBuilder = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinAnnotationClassSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier): KotlinAnnotationClassSpecBuilder = apply { delegate.addProperty(propertySpec.get()) }

  fun contextReceivers(vararg receiverTypes: TypeName): KotlinAnnotationClassSpecBuilder = builder { this.contextReceivers(*receiverTypes) }

  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }

  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }

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

@ExperimentalKotlinPoetApi
typealias KotlinAnnotationClassSpecBuilderReceiver = KotlinAnnotationClassSpecBuilder.() -> Unit
