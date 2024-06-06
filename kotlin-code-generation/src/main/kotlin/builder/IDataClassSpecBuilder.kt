package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.CodeBlock

internal interface IDataClassSpecBuilder<SELF , KotlinDataClassSpec> : Builder<KotlinDataClassSpec> {

  fun addKdoc(kdoc: CodeBlock) : SELF

}
