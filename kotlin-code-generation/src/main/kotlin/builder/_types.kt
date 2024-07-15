package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.poet.TypeSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinGeneratorSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinGeneratorTypeSpec
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

  fun addConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )

  fun addConstructorProperty(name: String, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )
}

interface KotlinDocumentableBuilder<SELF> {
  fun addKdoc(kdoc: KDoc): SELF
  fun addKDoc(kdoc: CodeBlock): SELF = addKdoc(KDoc(kdoc))
  fun addKdoc(docs: String) = addKdoc(KDoc.of(docs))
  fun addKdoc(format: String, first: String, vararg other: Any) = addKdoc(KDoc.of(format, first, *other))
}
