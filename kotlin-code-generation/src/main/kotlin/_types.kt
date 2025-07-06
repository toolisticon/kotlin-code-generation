package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import java.util.function.Supplier
import kotlin.reflect.KClass

/**
 * Marks a type as capable of building a new product.
 */
fun interface Builder<PRODUCT : Any> {
  fun build(): PRODUCT
}

/**
 * Builder for PRODUCT that is also a supplier for SPEC, used to combine poetSpec and kcg specs.
 */
interface BuilderSupplier<PRODUCT : Any, SPEC : Any> : Builder<PRODUCT>, Supplier<SPEC>

/**
 * Marks type as className holder.
 */
interface WithClassName {
  val className: ClassName
}

/**
 * An implementing type can provide a generic value for a tag key.
 * Behaves as [com.squareup.kotlinpoet.Taggable] but hides the implementation of the tg provider.
 */
interface WithTags {
  /**
   * @see [com.squareup.kotlinpoet.Taggable.tag]
   */
  fun <T : Any> tag(type: KClass<T>): T?
}

/**
 * Reified access to [WithTags.tag].
 */
inline fun <reified T : Any> WithTags.tag(): T? = tag(T::class)

/**
 * ClassName when used as a file name for fileSpec.
 */
typealias FileName = ClassName

/**
 * The poet string used for formatting.
 */
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

/**
 * A parameter name, the  name of fun parameter.
 */
typealias ParameterName = String

/**
 * A type alias name.
 */
typealias TypeAliasName = String
