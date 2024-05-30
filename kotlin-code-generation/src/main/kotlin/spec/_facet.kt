package io.toolisticon.kotlin.generation.spec

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

interface WithAnnotationSpecs {
  val annotations: List<KotlinAnnotationSpec>
}

interface WithClassName : WithTypeName {
  val className: ClassName

  override val typeName: TypeName get() = className
}

interface WithName {
  val name: String
}

interface WithType {
  val type: TypeName
}

interface WithTypeName {
  val typeName: TypeName
}
