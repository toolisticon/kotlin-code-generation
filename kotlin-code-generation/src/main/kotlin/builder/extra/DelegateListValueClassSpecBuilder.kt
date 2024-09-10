package io.toolisticon.kotlin.generation.builder.extra

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.SimpleName
import io.toolisticon.kotlin.generation.builder.KotlinAnnotatableDocumentableModifiableBuilder
import io.toolisticon.kotlin.generation.builder.KotlinGeneratorTypeSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinSuperInterfaceSupport
import io.toolisticon.kotlin.generation.builder.KotlinValueClassSpecBuilder
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class DelegateListValueClassSpecBuilder private constructor(
  private val delegate: KotlinValueClassSpecBuilder,
) : KotlinGeneratorTypeSpecBuilder<DelegateListValueClassSpecBuilder, KotlinValueClassSpec>,
  KotlinAnnotatableDocumentableModifiableBuilder<DelegateListValueClassSpecBuilder>,
  KotlinSuperInterfaceSupport<DelegateListValueClassSpecBuilder> {

  companion object {
    fun builder(name: SimpleName) = builder(className = simpleClassName(name))
    fun builder(className: ClassName) = DelegateListValueClassSpecBuilder(className)
  }

  internal constructor(className: ClassName) : this(
    delegate = KotlinValueClassSpecBuilder(className)
  )

  override fun build(): KotlinValueClassSpec {
    TODO("Not yet implemented")
  }

  // <overrides>
  override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec) }
  override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc) }
  override fun addModifiers(vararg modifiers: KModifier) = apply { delegate.addModifiers(*modifiers) }
  override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) = apply { delegate.addSuperinterface(superinterface, constructorParameter) }
  override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) = apply { this.delegate.addSuperinterface(superinterface, delegate) }
  override fun builder(block: TypeSpec.Builder.() -> Unit) = apply { delegate.builder(block) }
  override fun tag(type: KClass<*>, tag: Any?) = apply { delegate.tag(type, tag) }
  // </overrides>
}
