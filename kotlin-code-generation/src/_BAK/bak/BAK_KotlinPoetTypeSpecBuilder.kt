package io.toolisticon.kotlin.generation._BAK.bak

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation._BAK.KotlinPoetTypeSpec
import io.toolisticon.kotlin.generation._BAK.TypeSpecSupplier

@Deprecated("remove")
sealed class BAK_KotlinPoetTypeSpecBuilder<T : KotlinPoetTypeSpec>(
  delegate: TypeSpec.Builder
) : BAK_KotlinPoetSpecBuilder<BAK_KotlinPoetTypeSpecBuilder<T>, T, TypeSpec, TypeSpec.Builder>(
  delegate = delegate
), TypeSpecSupplier
//  AnnotatableSpecBuilder<KotlinPoetSpecBuilder<KotlinPoetTypeSpecBuilder<T>, T, TypeSpec, TypeSpec.Builder>, T, TypeSpec, TypeSpec.Builder>,
//, DocumentableSpecBuilder<KotlinPoetSpecBuilder<KotlinPoetTypeSpecBuilder<T>, T, TypeSpec, TypeSpec.Builder>, T, TypeSpec, TypeSpec.Builder>
{


  fun addAnnotation(annotationSpec: AnnotationSpec) = applySelf {
    delegate.addAnnotation(annotationSpec)
  }

  fun addKdoc(block: CodeBlock) = applySelf {
    delegate.addKdoc(block)
  }

  override fun get(): TypeSpec = build().get()
}
