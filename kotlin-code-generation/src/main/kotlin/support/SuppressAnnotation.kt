package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import com.squareup.kotlinpoet.joinToCode
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.poet.FormatSpecifier.asCodeBlock
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier

@ExperimentalKotlinPoetApi
data class SuppressAnnotation(val names: List<String> = emptyList()) : KotlinAnnotationSpecSupplier {
  constructor(name: String) : this(listOf(name))

  operator fun plus(name: String) = copy(names = (names + name).distinct())

  override fun spec(): KotlinAnnotationSpec = buildAnnotation(Suppress::class) {
    addMember(names.distinct().sorted().map { it.asCodeBlock() }.joinToCode())
  }
}
