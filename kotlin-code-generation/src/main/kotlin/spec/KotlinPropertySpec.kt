package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.builder.KotlinPropertyBuilder

@JvmInline
value class KotlinPropertySpec(private val spec: PropertySpec) : KotlinPoetSpec<PropertySpec>, PropertySpecSupplier, WithName {
  override val name: String get() = spec.name

  val type: TypeName get() = spec.type

  val mutable: Boolean get() = spec.mutable

  override fun get(): PropertySpec = spec

//  override val kdoc: CodeBlock = builder.kdoc.build()
//  override val annotations: List<AnnotationSpec> = builder.annotations.toImmutableList()
//  public val modifiers: Set<KModifier> = builder.modifiers.toImmutableSet()
//  public val typeVariables: List<TypeVariableName> = builder.typeVariables.toImmutableList()
//  public val initializer: CodeBlock? = builder.initializer
//  public val delegated: Boolean = builder.delegated
//  public val getter: FunSpec? = builder.getter
//  public val setter: FunSpec? = builder.setter
//  public val receiverType: TypeName? = builder.receiverType
}

fun KotlinPropertySpec.toBuilder() = KotlinPropertyBuilder.builder(spec = this)
