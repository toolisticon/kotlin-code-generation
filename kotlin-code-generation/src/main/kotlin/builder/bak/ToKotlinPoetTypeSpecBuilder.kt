package io.toolisticon.kotlin.generation.builder.bak

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.spec.KotlinPoetTypeSpec

@Deprecated("remove")
interface ToKotlinPoetTypeSpecBuilder<SPEC : KotlinPoetTypeSpec, BUILDER> {

  operator fun invoke(spec: SPEC, kind: TypeSpec.Kind = spec.get().kind, name: String? = spec.get().name): BUILDER
}
