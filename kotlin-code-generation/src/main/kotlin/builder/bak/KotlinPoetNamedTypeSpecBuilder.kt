package io.toolisticon.kotlin.generation.builder.bak

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.KotlinPoetNamedTypeSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

@Deprecated("remove")
sealed class KotlinPoetNamedTypeSpecBuilder<T : KotlinPoetNamedTypeSpec>(
    protected val className: ClassName,
    delegate: TypeSpec.Builder
) : BAK_KotlinPoetTypeSpecBuilder<T>(
  delegate = delegate
), TypeSpecSupplier
