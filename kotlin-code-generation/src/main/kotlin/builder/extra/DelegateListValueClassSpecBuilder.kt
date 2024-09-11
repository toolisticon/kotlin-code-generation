package io.toolisticon.kotlin.generation.builder.extra

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.simpleClassName
import io.toolisticon.kotlin.generation.SimpleName
import io.toolisticon.kotlin.generation.builder.KotlinAnnotatableDocumentableModifiableBuilder
import io.toolisticon.kotlin.generation.builder.KotlinFunSpecBuilder.Companion.constructorBuilder
import io.toolisticon.kotlin.generation.builder.KotlinGeneratorTypeSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinSuperInterfaceSupport
import io.toolisticon.kotlin.generation.builder.KotlinValueClassSpecBuilder
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinValueClassSpec
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class DelegateListValueClassSpecBuilder internal constructor(
    private val delegate: KotlinValueClassSpecBuilder,
    private val listType: TypeName,
) : KotlinGeneratorTypeSpecBuilder<DelegateListValueClassSpecBuilder, KotlinValueClassSpec>,
    KotlinAnnotatableDocumentableModifiableBuilder<DelegateListValueClassSpecBuilder>,
    KotlinSuperInterfaceSupport<DelegateListValueClassSpecBuilder> {

    companion object {
        fun builder(name: SimpleName, itemsType: TypeName) =
            builder(className = simpleClassName(name), itemsType = itemsType)

        fun builder(className: ClassName, itemsType: TypeName) = DelegateListValueClassSpecBuilder(
            className = className,
            itemsType = itemsType
        )
    }

    private var propertyName :String = "items"


    internal constructor(className: ClassName, itemsType: TypeName) : this(
        delegate = KotlinValueClassSpecBuilder(className),
        listType = List::class.asClassName().parameterizedBy(itemsType),
    )

    fun propertyName(propertyName: String) = apply {
        require(propertyName.isNotBlank()) { "Property name cannot be blank." }
        this.propertyName = propertyName
    }

    override fun build(): KotlinValueClassSpec {
        delegate.addConstructorProperty(propertyName, listType) {
            makePrivate()
        }
        return delegate.build {
            addSuperinterface(listType, delegate.constructorProperty.name)
        }
    }

    // <overrides>
    override fun addAnnotation(spec: KotlinAnnotationSpecSupplier) = apply { delegate.addAnnotation(spec) }
    override fun addKdoc(kdoc: KDoc) = apply { delegate.addKdoc(kdoc) }
    override fun addModifiers(vararg modifiers: KModifier) = apply { delegate.addModifiers(*modifiers) }
    override fun addSuperinterface(superinterface: TypeName, constructorParameter: String) =
        apply { delegate.addSuperinterface(superinterface, constructorParameter) }

    override fun addSuperinterface(superinterface: TypeName, delegate: CodeBlock) =
        apply { this.delegate.addSuperinterface(superinterface, delegate) }

    override fun builder(block: TypeSpec.Builder.() -> Unit) = apply { delegate.builder(block) }
    override fun addTag(type: KClass<*>, tag: Any?) = apply { delegate.addTag(type, tag) }
    // </overrides>
}

@ExperimentalKotlinPoetApi
typealias DelegateListValueClassSpecBuilderReceiver = DelegateListValueClassSpecBuilder.() -> Unit
