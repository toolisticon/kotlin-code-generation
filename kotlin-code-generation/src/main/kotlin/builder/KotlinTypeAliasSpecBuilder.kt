package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilder
import io.toolisticon.kotlin.generation.poet.TypeAliasSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpec
import io.toolisticon.kotlin.generation.spec.KotlinTypeAliasSpecSupplier
import kotlin.reflect.KClass

class KotlinTypeAliasSpecBuilder internal constructor(
  private val delegate: TypeAliasSpecBuilder
) : BuilderSupplier<KotlinTypeAliasSpec, TypeAliasSpec>,
  KotlinTypeAliasSpecSupplier,
  DelegatingBuilder<KotlinTypeAliasSpecBuilder, TypeAliasSpecBuilderReceiver>,
KotlinDocumentableBuilder<KotlinTypeAliasSpecBuilder>{

  companion object {

    fun builder(name: String, type: TypeName): KotlinTypeAliasSpecBuilder = KotlinTypeAliasSpecBuilder(
      delegate = TypeAliasSpecBuilder.builder(name, type)
    )

    fun builder(name: String, type: KClass<*>): KotlinTypeAliasSpecBuilder = builder(name, type.asTypeName())
  }


  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }

  override fun addKdoc(kdoc: KDoc): KotlinTypeAliasSpecBuilder = apply{
    delegate.addKdoc(kdoc.get())
  }

  fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) = builder { this.addTypeVariables(typeVariables) }
  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }


  override fun builder(block: TypeAliasSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build() = KotlinTypeAliasSpec(spec = delegate.build())
  override fun spec(): KotlinTypeAliasSpec = build()
  override fun get(): TypeAliasSpec = build().get()

}

typealias KotlinTypeAliasSpecBuilderReceiver = KotlinTypeAliasSpecBuilder.() -> Unit
