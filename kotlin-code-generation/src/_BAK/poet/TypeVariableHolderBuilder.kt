package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.TypeVariableName
import io.toolisticon.kotlin.generation.BuilderSupplier

@Suppress("UNCHECKED_CAST")
sealed interface TypeVariableHolderBuilder<SELF : TypeVariableHolderBuilder<SELF, S, B>, S : Any, B : Any>
  : BuilderSupplier<S, B> {
  val typeVariables: MutableList<TypeVariableName>

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): SELF = apply {
    this.typeVariables += typeVariables
  } as SELF

  fun addTypeVariable(typeVariable: TypeVariableName): SELF = apply {
    typeVariables += typeVariable
  } as SELF

}
