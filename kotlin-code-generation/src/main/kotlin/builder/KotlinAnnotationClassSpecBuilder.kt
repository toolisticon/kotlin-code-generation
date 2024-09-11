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
import kotlin.reflect.KClass

/**
 * Builder for [KotlinAnnotationClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinAnnotationClassSpecBuilder internal constructor(
  val className: ClassName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinAnnotationClassSpecBuilder, KotlinAnnotationClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<KotlinAnnotationClassSpecBuilder>,
  KotlinConstructorPropertySupport<KotlinAnnotationClassSpecBuilder>,
  KotlinContextReceivableBuilder<KotlinAnnotationClassSpecBuilder>,
  KotlinMemberSpecHolderBuilder<KotlinAnnotationClassSpecBuilder>,
  KotlinTypeSpecHolderBuilder<KotlinAnnotationClassSpecBuilder> {

  companion object {

    /**
     * Creates new builder.
     */
    fun builder(name: String): KotlinAnnotationClassSpecBuilder = builder(simpleClassName(name))

    /**
     * Creates new builder.
     */
    fun builder(className: ClassName): KotlinAnnotationClassSpecBuilder = KotlinAnnotationClassSpecBuilder(className = className)
  }

  internal constructor(className: ClassName) : this(className, TypeSpecBuilder.annotationBuilder(className))

  private lateinit var _retention: AnnotationRetention
  private val constructorProperties: LinkedHashMap<String, KotlinConstructorPropertySpecSupplier> = LinkedHashMap()
  private val targets: MutableSet<AnnotationTarget> = mutableSetOf()
  private var repeatable: Boolean = false
  private var mustBeDocumented: Boolean = false

  /**
   * Add mustBeDocumented.
   */
  fun mustBeDocumented() = apply { this.mustBeDocumented = true }

  /**
   * Add repeatable.
   */
  fun repeatable() = apply { this.repeatable = true }

  /**
   * Add retention.
   */
  fun retention(retention: AnnotationRetention) = apply { this._retention = retention }

  /**
   * Add target.
   */
  fun target(vararg targets: AnnotationTarget) = apply { this.targets.addAll(targets) }


  internal fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }

  override fun build(): KotlinAnnotationClassSpec {
    if (constructorProperties.isNotEmpty()) {
      val constructor = delegate.primaryConstructorWithProperties(toList(constructorProperties.values))
      delegate.primaryConstructor(constructor.build())
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

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec.get()) }
  override fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) = apply { constructorProperties[spec.name] = spec }
  override fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }
  override fun addFunction(funSpec: KotlinFunSpecSupplier) = apply { delegate.addFunction(funSpec.get()) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }
  override fun addProperty(propertySpec: KotlinPropertySpecSupplier) = apply { delegate.addProperty(propertySpec.get()) }
  override fun addType(typeSpec: TypeSpecSupplier) = builder { this.addType(typeSpec.get()) }
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }
  // </overrides>
}

@ExperimentalKotlinPoetApi
typealias KotlinAnnotationClassSpecBuilderReceiver = KotlinAnnotationClassSpecBuilder.() -> Unit
