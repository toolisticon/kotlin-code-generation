package io.toolisticon.kotlin.generation._ktx

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

object TypeNameKtx {

  operator fun ClassName.plus(suffix: String?) = ClassName(this.packageName, this.simpleNames)

  fun TypeName.nullable(nullable: Boolean = true): TypeName = this.copy(nullable = nullable)
}
