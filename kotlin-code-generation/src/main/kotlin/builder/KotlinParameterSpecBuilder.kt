package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilder
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.ParameterSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpec
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpecSupplier
import java.lang.reflect.Type
import kotlin.reflect.KClass

class KotlinParameterSpecBuilder internal constructor(
  private val delegate: ParameterSpecBuilder
) : BuilderSupplier<KotlinParameterSpec, ParameterSpec>,
  KotlinParameterSpecSupplier,
  DelegatingBuilder<KotlinParameterSpecBuilder, ParameterSpecBuilderReceiver> {
  companion object {

    @JvmStatic
    fun builder(
      name: String,
      type: TypeName,
      vararg modifiers: KModifier
    ): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type, *modifiers)
    )

    @JvmStatic
    fun builder(
      name: String,
      type: Type,
      vararg modifiers: KModifier
    ): KotlinParameterSpecBuilder = builder(name, type.asTypeName(), *modifiers)

    @JvmStatic
    fun builder(
      name: String,
      type: KClass<*>,
      vararg modifiers: KModifier,
    ): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type.asTypeName(), *modifiers)
    )

    @JvmStatic
    fun builder(
      name: String,
      type: TypeName,
      modifiers: Iterable<KModifier>,
    ): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type, modifiers)
    )

    @JvmStatic
    fun builder(
      name: String,
      type: Type,
      modifiers: Iterable<KModifier>,
    ): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type.asTypeName(), modifiers)
    )

    @JvmStatic
    fun builder(
      name: String,
      type: KClass<*>,
      modifiers: Iterable<KModifier>,
    ): KotlinParameterSpecBuilder = KotlinParameterSpecBuilder(
      delegate = ParameterSpecBuilder.builder(name, type.asTypeName(), modifiers)
    )


    @JvmStatic
    fun builder(spec: KotlinParameterSpec) = builder(spec.get())

    @JvmStatic
    fun builder(spec: ParameterSpec) = KotlinParameterSpecBuilder(delegate = spec.toBuilder().wrap())
  }

  override fun builder(block: ParameterSpecBuilderReceiver) = apply {
    delegate { block() }
  }


  fun addAnnotation(annotationSpec: KotlinAnnotationSpecSupplier): KotlinParameterSpecBuilder = builder {
    addAnnotation(annotationSpec.get())
  }

  override fun build(): KotlinParameterSpec = KotlinParameterSpec(spec = delegate.build())
  override fun spec(): KotlinParameterSpec = build()
  override fun get(): ParameterSpec = build().get()
}
