package io.toolisticon.kotlin.generation._BAK

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.Builder
import io.toolisticon.kotlin.generation._BAK.poet.PropertySpecBuilder
import io.toolisticon.kotlin.generation._BAK.KotlinPropertySpec
import io.toolisticon.kotlin.generation._BAK.PropertySpecSupplier

class KotlinPropertySpecBuilder(
  private val name: String,
  private val type: TypeName,
  private val delegate: PropertySpecBuilder
) : Builder<KotlinPropertySpec>, PropertySpecSupplier {
  companion object {
    fun builder(name: String, type: TypeName, vararg modifiers: KModifier): KotlinPropertySpecBuilder = KotlinPropertySpecBuilder(
      name = name,
      type = type,
      delegate = PropertySpecBuilder.builder(name, type, *modifiers)
    )
  }

  operator fun invoke(block: PropertySpecBuilder.() -> Unit): KotlinPropertySpecBuilder = apply {
    delegate.block()
  }

  fun addAnnotation(annotationSpec: AnnotationSpec): KotlinPropertySpecBuilder = invoke {
    addAnnotation(annotationSpec)
  }

  fun makePrivate() = invoke {
    addModifiers(KModifier.PRIVATE)
  }

  fun makeFinal() = invoke {
    addModifiers(KModifier.PRIVATE)
  }

  override fun build(): KotlinPropertySpec = KotlinPropertySpec(spec = delegate.build())
  override fun get(): PropertySpec = build().get()
}
