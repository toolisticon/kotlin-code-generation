package io.toolisticon.kotlin.generation.support

/**
 * Suppress unusual class name (eg lower case)
 */
const val SUPPRESS_CLASS_NAME = "ClassName"

const val SUPPRESS_MEMBER_VISIBILITY_CAN_BE_PRIVATE = "MemberVisibilityCanBePrivate"

/**
 * Suppress redundant visibility modifiers - the public keywords used by kotlinpoet.
 */
const val SUPPRESS_REDUNDANT_VISIBILITY_MODIFIER = "RedundantVisibilityModifier"

/**
 * We are sure that the cast will work although the compiler warns about it.
 */
const val SUPPRESS_UNCHECKED_CAST = "UNCHECKED_CAST"

/**
 * Suppress warnings on unused methods and types.
 */
const val SUPPRESS_UNUSED = "unused"
