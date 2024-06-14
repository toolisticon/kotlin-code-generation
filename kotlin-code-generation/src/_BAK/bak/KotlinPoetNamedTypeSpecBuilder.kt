package io.toolisticon.kotlin.generation._BAK.bak

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation._BAK.KotlinPoetNamedTypeSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier

@Deprecated("remove")
sealed class KotlinPoetNamedTypeSpecBuilder<T : KotlinPoetNamedTypeSpec>(
    protected val className: ClassName,
    delegate: TypeSpec.Builder
) : BAK_KotlinPoetTypeSpecBuilder<T>(
  delegate = delegate
), TypeSpecSupplier
