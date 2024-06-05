package io.toolisticon.kotlin.generation.builder.bak

@Deprecated("remove")
interface ToKotlinPoetSpecBuilder<SPEC, BUILDER> {
  operator fun invoke(spec: SPEC): BUILDER
}
