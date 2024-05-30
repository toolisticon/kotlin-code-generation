package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.builder.KotlinPropertyBuilder

@JvmInline
value class KotlinPropertySpec(private val spec: PropertySpec) : KotlinPoetSpec<PropertySpec>,
  PropertySpecSupplier,
  WithName,
  WithAnnotationSpecs {
  override val name: String get() = spec.name

  val type: TypeName get() = spec.type

  val mutable: Boolean get() = spec.mutable


  override fun get(): PropertySpec = spec
  override val annotations: List<KotlinAnnotationSpec> get() = KotlinAnnotationSpec.of(spec.annotations)

//  override val kdoc: CodeBlock = builder.kdoc.build()
//  public val modifiers: Set<KModifier> = builder.modifiers.toImmutableSet()
//  public val typeVariables: List<TypeVariableName> = builder.typeVariables.toImmutableList()
//  public val initializer: CodeBlock? = builder.initializer
//  public val delegated: Boolean = builder.delegated
//  public val getter: FunSpec? = builder.getter
//  public val setter: FunSpec? = builder.setter
//  public val receiverType: TypeName? = builder.receiverType
}

fun KotlinPropertySpec.toBuilder() = KotlinPropertyBuilder.builder(spec = this)
