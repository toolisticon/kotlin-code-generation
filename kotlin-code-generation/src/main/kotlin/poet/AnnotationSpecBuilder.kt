package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.AnnotationSpec.UseSiteTarget
import kotlin.reflect.KClass

/**
 * Wraps [AnnotationSpec.Builder] for typesafe access.
 */
class AnnotationSpecBuilder(
  override val builder: AnnotationSpec.Builder
) : PoetSpecBuilder<AnnotationSpecBuilder, AnnotationSpec.Builder, AnnotationSpec, AnnotationSpecSupplier>,
  AnnotationSpecSupplier,
  PoetTaggableBuilder<AnnotationSpecBuilder> {
  companion object {
    internal fun AnnotationSpec.Builder.wrap() = AnnotationSpecBuilder(this)

    /**
     * Creates new builder.
     */
    fun builder(type: ClassName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()

    /**
     * Creates new builder.
     */
    fun builder(type: ParameterizedTypeName): AnnotationSpecBuilder = AnnotationSpec.builder(type).wrap()

    /**
     * Creates new builder.
     */
    fun builder(type: KClass<out Annotation>): AnnotationSpecBuilder = builder(type.asClassName())
  }

  // Taggable
  override fun tag(type: KClass<*>, tag: Any?) = apply { builder.tag(type, tag) }

  // Annotatable
  fun addMember(format: String, vararg args: Any) = apply { builder.addMember(CodeBlock.of(format, *args)) }
  fun addMember(codeBlock: CodeBlock) = apply { builder.addMember(codeBlock) }
  fun useSiteTarget(useSiteTarget: UseSiteTarget?) = apply { builder.useSiteTarget(useSiteTarget) }

  /**
   * Remove all registered members.
   */
  fun clearMembers() = apply {
    builder.members.clear()
  }

  override fun build(): AnnotationSpec = builder.build()
}

interface AnnotationSpecSupplier : PoetSpecSupplier<AnnotationSpec>
typealias AnnotationSpecBuilderReceiver = AnnotationSpec.Builder.() -> Unit
