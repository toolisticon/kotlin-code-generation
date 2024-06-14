package io.toolisticon.kotlin.generation._BAK.bak

@Deprecated("remove")
interface ToKotlinPoetSpecBuilder<SPEC, BUILDER> {
  operator fun invoke(spec: SPEC): BUILDER
}
