package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.FunctionName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildFun
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildProperty
import io.toolisticon.kotlin.generation.PropertyName
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.*
import kotlin.reflect.KClass

interface DelegatingBuilder<SELF, RECEIVER> {
  fun builder(block: RECEIVER): SELF
}

interface KotlinGeneratorTypeSpecBuilder<SELF, SPEC : KotlinGeneratorTypeSpec<SPEC>> : BuilderSupplier<SPEC, TypeSpec>,
  DelegatingBuilder<SELF, TypeSpecBuilderReceiver>,
  KotlinGeneratorSpecSupplier<SPEC> {
  override fun spec(): SPEC = build()

  override fun get(): TypeSpec = spec().get()
}

interface ConstructorPropertySupport<SELF> {

  fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier): SELF

  fun addConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}): SELF = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )

  fun addConstructorProperty(name: String, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}): SELF = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )
}

/**
 * Typesafe wrapper for [com.squareup.kotlinpoet.Documentable.Builder]. Marks anything that can have `kdoc` documentation.
 *
 * * `addKdoc`
 */
interface KotlinDocumentableBuilder<SELF> {
  fun addKdoc(kdoc: KDoc): SELF
  fun addKDoc(kdoc: CodeBlock): SELF = addKdoc(KDoc(kdoc))
  fun addKdoc(docs: String): SELF = addKdoc(KDoc.of(docs))
  fun addKdoc(format: String, first: String, vararg other: Any): SELF = addKdoc(KDoc.of(format, first, *other))
}

/**
 * Typesafe wrapper for [com.squareup.kotlinpoet.MemberSpecHolder.Builder].
 *
 * * `addFunction`
 * * `addProperty`
 */
interface KotlinMemberSpecHolderBuilder<SELF> {
  fun addFunction(funSpec: KotlinFunSpecSupplier): SELF
  fun addFunction(name: FunctionName, block: KotlinFunSpecBuilderReceiver): SELF = addFunction(funSpec = buildFun(name, block))

  fun addProperty(propertySpec: KotlinPropertySpecSupplier): SELF
  fun addProperty(name: PropertyName, type: TypeName, block: KotlinPropertySpecBuilderReceiver): SELF = addProperty(propertySpec = buildProperty(name, type, block))
  fun addProperty(name: PropertyName, type: KClass<*>, block: KotlinPropertySpecBuilderReceiver): SELF = addProperty(propertySpec = buildProperty(name, type, block))

}
