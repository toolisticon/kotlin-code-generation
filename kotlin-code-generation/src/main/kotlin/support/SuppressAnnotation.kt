package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.joinToCode
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.poet.FormatSpecifier.asCodeBlock
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier

data class SuppressAnnotation(val names: List<String> = emptyList()) : KotlinAnnotationSpecSupplier {
  companion object {
    /**
     * Suppress unusual class name (eg lower case)
     */
    const val CLASS_NAME = "ClassName"

    const val MEMBER_VISIBILITY_CAN_BE_PRIVATE = "MemberVisibilityCanBePrivate"

    /**
     * Suppress redundant visibility modifiers - the public keywords used by kotlinpoet.
     */
    const val REDUNDANT_VISIBILITY_MODIFIER = "RedundantVisibilityModifier"

    /**
     * We are sure that the cast will work although the compiler warns about it.
     */
    const val UNCHECKED_CAST = "UNCHECKED_CAST"

    /**
     * Suppress warnings on unused methods and types.
     */
    const val UNUSED = "unused"
  }

  constructor(name: String) : this(listOf(name))

  operator fun plus(name: String) = copy(names = (names + name).distinct())

  override fun spec(): KotlinAnnotationSpec = buildAnnotation(Suppress::class) {
    addMember(names.distinct().sorted().map { it.asCodeBlock() }.joinToCode())
  }
}
