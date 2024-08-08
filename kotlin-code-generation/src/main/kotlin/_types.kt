package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import java.util.function.Supplier


/**
 * Marks a type as capable of building a new product.
 */
fun interface Builder<PRODUCT : Any> {
  fun build(): PRODUCT
}

interface BuilderSupplier<PRODUCT : Any, SPEC : Any> : Builder<PRODUCT>, Supplier<SPEC>

interface WithClassName {
  val className: ClassName
}

typealias CodeBlockFormat = String
typealias FunctionName = String
typealias PackageName = String
typealias PropertyName = String
typealias SimpleName = String
