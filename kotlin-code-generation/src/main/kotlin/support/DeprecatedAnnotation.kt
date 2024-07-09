package io.toolisticon.kotlin.generation.support

import io.toolisticon.kotlin.generation.KotlinCodeGeneration
import io.toolisticon.kotlin.generation.poet.FormatSpecifier.asCodeBlock
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier

data class DeprecatedAnnotation(val message: String, val deprecationLevel: DeprecationLevel? = null) : KotlinAnnotationSpecSupplier {
  override fun spec(): KotlinAnnotationSpec = KotlinCodeGeneration.buildAnnotation(Deprecated::class) {
    if (deprecationLevel == null) {
      addMember(message.asCodeBlock())
    } else {
      addStringMember("message", message)
      addEnumMember("deprecationLevel", deprecationLevel)
    }
  }
}
