package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.Builder
import java.util.function.Supplier
import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * Unfortunately, kotlin-poet specs do not share a common interface, this alias simulates that.
 */
typealias PoetSpec = Any

/**
 * Any class that can provide a kotlin-poet-native type like [com.squareup.kotlinpoet.TypeSpec], [com.squareup.kotlinpoet.ParameterSpec], ...
 * exposes this by implementing this interface.
 */
interface PoetSpecSupplier<SPEC : PoetSpec> : Supplier<SPEC>

/**
 * Unfortunately, kotlin-poet spec-builders do not share a common interface, this interface simulates that.
 */
sealed interface PoetSpecBuilder<SELF : PoetSpecBuilder<SELF, BUILDER, SPEC, SUPPLIER>, BUILDER, SPEC : PoetSpec, SUPPLIER : PoetSpecSupplier<SPEC>> : Builder<SPEC>, PoetSpecSupplier<SPEC> {
  val builder: BUILDER

  override fun get(): SPEC = build()
}

sealed interface AnnotatableBuilder<SELF> {

  fun addAnnotation(annotationSpec: AnnotationSpec): SELF

  fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>): SELF

  fun addAnnotation(annotation: ClassName): SELF = addAnnotation(AnnotationSpec.builder(annotation).build())

  fun addAnnotation(annotation: KClass<*>): SELF = addAnnotation(annotation.asClassName())

  fun addAnnotation(annotationSupplier: AnnotationSpecSupplier): SELF = addAnnotation(annotationSupplier.get())
}

sealed interface TypeSpecHolderBuilder<SELF> {
  fun addType(typeSpec: TypeSpec): SELF

  fun addTypes(typeSpecs: Iterable<TypeSpec>): SELF
}

sealed interface MemberSpecHolderBuilder<SELF> {
  fun addFunction(funSpec: FunSpec): SELF
  fun addFunctions(funSpecs: Iterable<FunSpec>): SELF

  fun addProperties(propertySpecs: Iterable<PropertySpec>): SELF
  fun addProperty(propertySpec: PropertySpec): SELF

  fun addProperty(name: String, type: TypeName, vararg modifiers: KModifier) = addProperty(PropertySpec.builder(name, type, *modifiers).build())
  fun addProperty(name: String, type: KClass<*>, vararg modifiers: KModifier) = addProperty(name, type.asTypeName(), *modifiers)
  fun addProperty(name: String, type: TypeName, modifiers: Iterable<KModifier>) = addProperty(PropertySpec.builder(name, type, modifiers).build())
  fun addProperty(name: String, type: KClass<*>, modifiers: Iterable<KModifier>) = addProperty(name, type.asTypeName(), modifiers)
}

sealed interface OriginatingElementsHolderBuilder<SELF> {
  fun addOriginatingElement(originatingElement: Element): SELF
}

sealed interface ContextReceivableBuilder<SELF> {

  @ExperimentalKotlinPoetApi
  fun contextReceivers(receiverTypes: Iterable<TypeName>): SELF

  @ExperimentalKotlinPoetApi
  fun contextReceivers(vararg receiverTypes: TypeName): SELF
}

sealed interface DocumentableBuilder<SELF> {
  fun addKdoc(format: String, vararg args: Any): SELF
  fun addKdoc(block: CodeBlock): SELF
}
