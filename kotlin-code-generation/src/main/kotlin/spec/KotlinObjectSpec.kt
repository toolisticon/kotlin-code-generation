package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.TypeSpec



@JvmInline
value class KotlinObjectSpec(override val spec: TypeSpec) : KotlinPoetTypeSpec {
}
