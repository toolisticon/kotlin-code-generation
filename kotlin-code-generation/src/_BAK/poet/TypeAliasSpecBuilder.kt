package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.TypeAliasSpec.Builder
import io.toolisticon.kotlin.generation.BuilderSupplier
import kotlin.reflect.KClass

typealias TypeAliasSpecBuilderReceiver = TypeAliasSpecBuilder.() -> Unit

@JvmInline
value class TypeAliasSpecBuilder(private val builder: Builder) : BuilderSupplier<TypeAliasSpec, Builder>,
    AnnotatableBuilder<TypeAliasSpecBuilder, TypeAliasSpec, Builder>,
    DocumentableBuilder<TypeAliasSpecBuilder, TypeAliasSpec, Builder>,
    KModifierHolderBuilder<TypeAliasSpecBuilder, TypeAliasSpec, Builder>,
    TaggableBuilder<TypeAliasSpecBuilder, TypeAliasSpec, Builder>,
    TypeVariableHolderBuilder<TypeAliasSpecBuilder, TypeAliasSpec, Builder> {
  companion object {
    private fun Builder.wrap() = TypeAliasSpecBuilder(this)
    fun builder(name: String, type: TypeName): TypeAliasSpecBuilder = TypeAliasSpec.builder(name, type).wrap()
    fun builder(name: String, type: KClass<*>): TypeAliasSpecBuilder = builder(name, type.asTypeName())
  }

  override val modifiers: MutableList<KModifier> get() = builder.modifiers.toMutableList()
  override val typeVariables: MutableList<TypeVariableName> get() = builder.typeVariables.toMutableList()

  override fun build(): TypeAliasSpec = builder.build()
  override fun get(): Builder = builder
}
