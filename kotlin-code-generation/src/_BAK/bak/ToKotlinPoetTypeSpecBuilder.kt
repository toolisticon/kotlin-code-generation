package io.toolisticon.kotlin.generation._BAK.bak

import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation._BAK.KotlinPoetTypeSpec

@Deprecated("remove")
interface ToKotlinPoetTypeSpecBuilder<SPEC : KotlinPoetTypeSpec, BUILDER> {

  operator fun invoke(spec: SPEC, kind: TypeSpec.Kind = spec.get().kind, name: String? = spec.get().name): BUILDER
}
