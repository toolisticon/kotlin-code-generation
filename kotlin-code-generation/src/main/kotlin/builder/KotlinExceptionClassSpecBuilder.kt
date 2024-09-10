package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_NAME
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.nullable
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinExceptionClassSpec
import kotlin.reflect.KClass

/**
 * Builder for [KotlinExceptionClassSpec].
 */
@ExperimentalKotlinPoetApi
class KotlinExceptionClassSpecBuilder internal constructor(
  private val className: ClassName,
  private val extends: TypeName,
  private val delegate: TypeSpecBuilder
) : KotlinGeneratorTypeSpecBuilder<KotlinExceptionClassSpecBuilder, KotlinExceptionClassSpec>,
  KotlinAnnotatableBuilder<KotlinExceptionClassSpecBuilder>,
  KotlinDocumentableBuilder<KotlinExceptionClassSpecBuilder>,
  KotlinModifiableBuilder<KotlinExceptionClassSpecBuilder>,
  KotlinSuperInterfaceSupport<KotlinExceptionClassSpecBuilder> {

  companion object {
    val runtimeExceptionName = RuntimeException::class.asTypeName()

    fun builder(name: String, extends: TypeName = runtimeExceptionName): KotlinExceptionClassSpecBuilder = builder(simpleClassName(name), runtimeExceptionName)
    fun builder(name: String, extends: KClass<out Exception>): KotlinExceptionClassSpecBuilder = builder(simpleClassName(name), extends.asTypeName())
    fun builder(className: ClassName, extends: KClass<out Exception>): KotlinExceptionClassSpecBuilder = KotlinExceptionClassSpecBuilder(className = className, extends = extends.asTypeName())
    fun builder(className: ClassName, extends: TypeName = runtimeExceptionName): KotlinExceptionClassSpecBuilder = KotlinExceptionClassSpecBuilder(className = className, extends = extends)

    private val FIND_TEMPLATE_PARAMS = Regex("\\$(\\w+)")
    private fun findTemplateParams(template: String) = FIND_TEMPLATE_PARAMS.findAll(template).map { it.value }
      .map { it.removePrefix("$") }
  }

  internal constructor(className: ClassName, extends: TypeName) : this(
    className = className,
    extends = extends,
    delegate = TypeSpecBuilder.classBuilder(className).superclass(extends)
  )

  private lateinit var _messageTemplate: String

  private var causeName: String = "cause"
  private var _includeCause: Boolean = false
  private val _templateParameters = mutableMapOf<String, TypeName>()

  fun messageTemplate(messageTemplate: String): KotlinExceptionClassSpecBuilder = apply {
    this._messageTemplate = messageTemplate

    findTemplateParams(_messageTemplate).forEach {
      _templateParameters.putIfAbsent(it, Any::class.asTypeName())
    }
  }

  fun includeCause(name: String? = null) = apply {
    name?.let { causeName = it }
    _includeCause = true
  }

  fun addParameter(name: String, type: KClass<*>) = addParameter(name, type.asTypeName())

  fun addParameter(name: String, type: TypeName) = apply {
    _templateParameters[name] = type
  }

  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier): KotlinExceptionClassSpecBuilder = apply { delegate.addAnnotation(spec.get()) }
  override fun addKdoc(kdoc: KDoc): KotlinExceptionClassSpecBuilder = apply { delegate.addKdoc(kdoc.get()) }
  override fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }

  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = builder { this.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = builder { this.addSuperinterface(superinterface, delegate) }

  fun addInitializerBlock(block: CodeBlock) = builder { this.addInitializerBlock(block) }

  override fun builder(block: TypeSpecBuilderReceiver) = apply { delegate.builder.block() }

  override fun build(): KotlinExceptionClassSpec {
    require(::_messageTemplate.isInitialized)
    delegate.addSuperclassConstructorParameter(KotlinCodeGeneration.format.FORMAT_STRING_TEMPLATE, _messageTemplate)

    val constructorBuilder: KotlinFunSpecBuilder = _templateParameters.entries.fold(constructorBuilder()) { acc, cur ->
      acc.addParameter(cur.key, cur.value)
    }

    if (_includeCause) {
      delegate.addSuperclassConstructorParameter(FORMAT_NAME, causeName)

      constructorBuilder.addParameter(KotlinCodeGeneration.buildParameter(causeName, Throwable::class.asTypeName().nullable()) {
        defaultValue("null")
      })
    }

    return KotlinExceptionClassSpec(
      className = className,
      extends = extends,
      spec = delegate
        .primaryConstructor(constructorBuilder.get())
        .build()
    )
  }
}

@ExperimentalKotlinPoetApi
typealias KotlinExceptionClassSpecBuilderReceiver = KotlinExceptionClassSpecBuilder.() -> Unit
