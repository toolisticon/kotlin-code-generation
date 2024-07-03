package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import kotlin.reflect.KClass


interface DelegatingBuilder<SELF, RECEIVER> {
  fun builder(block: RECEIVER): SELF
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

//BuilderSupplier<KotlinAnonymousClassSpec, TypeSpec>,
//KotlinAnonymousClassSpecSupplier,
//DelegatingBuilder<KotlinAnonymousClassSpecBuilder, TypeSpecBuilderReceiver> {
//
//  BuilderSupplier<KotlinAnnotationSpec, AnnotationSpec>,
//  KotlinAnnotationSpecSupplier,
//  DelegatingBuilder<KotlinAnnotationSpecBuilder, AnnotationSpecBuilderReceiver> {}
//
//sealed class KotlinCodeGenerationSpecBuilder<
//  SELF : Any,
//  POET_SPEC : Any,
//  SPEC: KotlinAnnotationSpec,
//  POET_BUILDER,
//  POET_BUILDER_RECCEIVER
//  >(protected val delegate: POET_BUILDER) :
//  DelegatingBuilder<SELF, POET_BUILDER_RECCEIVER>, BuilderSupplier<SELF, POET_SPEC> {
//
//}

// FIXME
//class FooSpecBuilder internal constructor(
//  delegate: AnnotationSpecBuilder
//) : KotlinCodeGenerationSpecBuilder<
//  FooSpecBuilder,
//  AnnotationSpec,
//  AnnotationSpecBuilder,
//  AnnotationSpecBuilderReceiver
//  >(delegate)
//{
//  override fun builder(block: AnnotationSpecBuilderReceiver): FooSpecBuilder {
//    TODO("Not yet implemented")
//  }
//
//  override fun build(): FooSpecBuilder {
//    TODO("Not yet implemented")
//  }
//
//  override fun get(): AnnotationSpec = build().get()
//
//}
