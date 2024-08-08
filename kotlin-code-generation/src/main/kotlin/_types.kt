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

/**
 * A function name, like `doSomething`().
 */
typealias FunctionName = String

/**
 * The package part of a FQN (e.g. `de.foo.bar`).
 */
typealias PackageName = String

/**
 * The simple part of an FQN (e.g. `MyClass`).
 */
typealias SimpleName = String

/**
 * A property name, the field name of a property.
 */
typealias PropertyName = String
