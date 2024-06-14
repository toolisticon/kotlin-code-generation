package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.CodeBlock

internal interface IDataClassSpecBuilder<SELF , KotlinDataClassSpec> : Builder<KotlinDataClassSpec> {

  fun addKdoc(kdoc: CodeBlock) : SELF

}
