package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationClassSpec
import io.toolisticon.kotlin.generation.spec.KotlinEnumClassSpec
import io.toolisticon.kotlin.generation.spec.TypeSpecSupplier

class KotlinEnumClassBuilder internal constructor(delegate: TypeSpec.Builder) : KotlinPoetTypeSpecBuilder<KotlinEnumClassSpec>(
  delegate = delegate
), TypeSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetTypeSpecBuilder<KotlinEnumClassSpec, KotlinEnumClassBuilder> {
    override fun invoke(spec: KotlinEnumClassSpec, kind: TypeSpec.Kind, name: String?): KotlinEnumClassBuilder = KotlinEnumClassBuilder(spec.get().toBuilder(kind,name))
  }


  companion object {
    fun builder(other: TypeSpec.Builder, block: TypeSpecBuilderReceiver = {}) = with(KotlinEnumClassBuilder(delegate = other)) {
      invoke(block)
    }

    fun builder(className: ClassName) = KotlinEnumClassBuilder(delegate = TypeSpec.enumBuilder(className))

  }


  fun addKdoc(kdoc: CodeBlock) = apply {
    delegate.addKdoc(kdoc)
  }

  fun addEnumConstant(name: String) = apply {
    delegate.addEnumConstant(name)
  }


  override fun build(): KotlinEnumClassSpec = KotlinEnumClassSpec(delegate.build())

}
