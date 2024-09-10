package io.toolisticon.kotlin.generation.builder.extra

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildParameter
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_NAME
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING_TEMPLATE
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.nullable
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.builder.KotlinAnnotatableDocumentableModifiableBuilder
import io.toolisticon.kotlin.generation.builder.KotlinClassSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinFunSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinGeneratorTypeSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinSuperInterfaceSupport
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.spec.ClassSpecType
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinClassSpec
import kotlin.reflect.KClass

/**
 * Builder for a special [io.toolisticon.kotlin.generation.spec.KotlinClassSpec] that represents
 * an [RuntimeException] with String-Message-Template.
 */
@ExperimentalKotlinPoetApi
class RuntimeExceptionSpecBuilder internal constructor(
  private val delegate: KotlinClassSpecBuilder,
) : KotlinGeneratorTypeSpecBuilder<RuntimeExceptionSpecBuilder, KotlinClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<RuntimeExceptionSpecBuilder>,
  KotlinSuperInterfaceSupport<RuntimeExceptionSpecBuilder> {
  companion object {
    private val NULLABLE_THROWABLE = Throwable::class.asTypeName().nullable()
    private val FIND_TEMPLATE_PARAMS = Regex("\\$(\\w+)")

    fun builder(name: String): RuntimeExceptionSpecBuilder = builder(simpleClassName(name))
    fun builder(className: ClassName): RuntimeExceptionSpecBuilder = RuntimeExceptionSpecBuilder(className = className)

    /**
     * Gets template parameters as names.
     */
    private fun findTemplateParams(template: String) = FIND_TEMPLATE_PARAMS.findAll(template).map { it.value }
      .map { it.removePrefix("$") }
  }

  private lateinit var _messageTemplate: String
  private var _cause: Pair<Boolean, String> = false to "cause"
  private val _messageTemplateParameters = LinkedHashMap<String, TypeName>()

  internal constructor(className: ClassName) : this(delegate = KotlinClassSpecBuilder(className = className)) {
    tag(ClassSpecType.EXCEPTION)
    delegate.superclass(RuntimeException::class)
  }

  fun messageTemplate(messageTemplate: String) = apply {
    _messageTemplate = messageTemplate

    findTemplateParams(_messageTemplate).forEach {
      _messageTemplateParameters.putIfAbsent(it, Any::class.asTypeName())
    }
  }

  fun addParameter(name: String, type: KClass<*>) = addParameter(name, type.asTypeName())

  fun addParameter(name: String, type: TypeName) = apply {
    _messageTemplateParameters[name] = type
  }

  fun includeCause(name: String? = null) = apply {
    _cause = true to (name ?: _cause.second)
  }

  override fun build(): KotlinClassSpec {
    require(::_messageTemplate.isInitialized) { "Message template must be initialized." }
    delegate.addSuperclassConstructorParameter(FORMAT_STRING_TEMPLATE, _messageTemplate)

    val constructorBuilder: KotlinFunSpecBuilder = _messageTemplateParameters.entries.fold(constructorBuilder()) { acc, cur ->
      acc.addParameter(cur.key, cur.value)
    }

    if (_cause.first) {
      delegate.addSuperclassConstructorParameter(FORMAT_NAME, _cause.second)
      val nullableCauseParameter = buildParameter(_cause.second, NULLABLE_THROWABLE) {
        defaultValue("null")
      }

      constructorBuilder.addParameter(nullableCauseParameter)
    }

    return delegate.primaryConstructor(constructorBuilder).build()
  }

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc) }
  override fun addModifiers(vararg modifiers: KModifier) = apply { delegate.addModifiers(*modifiers) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = apply { delegate.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock): RuntimeExceptionSpecBuilder {
    TODO("Not yet implemented")
  }

  //override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) : RuntimeExceptionSpecBuilder= apply { delegate.addS }
  override fun tag(type: KClass<*>, tag: Any?) = apply { delegate.tag(type, tag) }
  override fun builder(block: TypeSpec.Builder.() -> Unit): RuntimeExceptionSpecBuilder = apply { delegate.builder(block) }
  // </overrides>
}

@ExperimentalKotlinPoetApi
typealias RuntimeExceptionSpecBuilderReceiver = RuntimeExceptionSpecBuilder.() -> Unit