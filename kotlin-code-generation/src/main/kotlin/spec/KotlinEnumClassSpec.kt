package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec

@JvmInline
value class KotlinEnumClassSpec(override val spec: TypeSpec) : KotlinPoetTypeSpec {
}
