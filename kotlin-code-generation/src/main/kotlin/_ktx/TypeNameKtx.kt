package io.toolisticon.kotlin.generation._ktx

import com.squareup.kotlinpoet.ClassName

object TypeNameKtx {

  operator fun ClassName.plus(suffix: String?) = ClassName(this.packageName, this.simpleNames)

}
