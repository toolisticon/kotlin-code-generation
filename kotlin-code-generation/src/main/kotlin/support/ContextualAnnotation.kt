package io.toolisticon.kotlin.generation.support

import com.squareup.kotlinpoet.ExperimentalKotlinPoetApi
import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildAnnotation
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import kotlinx.serialization.Contextual

// TODO: we could also use UseContextualSerialization on the generated file if we collect all classes that require custom serialization in that file.

/**
 * Builder to create a `@Contextual` annotation.
 */
@ExperimentalKotlinPoetApi
object ContextualAnnotation : KotlinAnnotationSpecSupplier{
  override fun spec(): KotlinAnnotationSpec = buildAnnotation(Contextual::class)
}
