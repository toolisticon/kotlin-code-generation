package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asMemberName
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import kotlin.reflect.KClass

class KotlinAnnotationSpecBuilder internal constructor(
  private val delegate: AnnotationSpecBuilder
) : BuilderSupplier<KotlinAnnotationSpec, AnnotationSpec>, KotlinAnnotationSpecSupplier, DelegatingBuilder<KotlinAnnotationSpecBuilder, AnnotationSpecBuilderReceiver> {
  companion object {
    @JvmStatic
    fun builder(
      type: ClassName
    ): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    @JvmStatic
    fun builder(
      type: ParameterizedTypeName
    ): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    @JvmStatic
    fun builder(
      type: KClass<out Annotation>
    ): KotlinAnnotationSpecBuilder = builder(type.asClassName())

    fun from(spec: KotlinAnnotationSpecSupplier) = KotlinAnnotationSpecBuilder(
      delegate = spec.get().toBuilder().wrap()
    )
  }

  fun addMember(format: String, vararg args: Any): KotlinAnnotationSpecBuilder = apply {
    delegate.addMember(format, *args)
  }

  fun addMember(codeBlock: CodeBlock): KotlinAnnotationSpecBuilder = apply {
    delegate.addMember(codeBlock)
  }

  fun addMember(memberName: MemberName): KotlinAnnotationSpecBuilder = addMember("%M", memberName)

  fun addKClassMember(name: String, klass: KClass<*>) = addMember("$name = %T::class", klass)

  fun addStringMember(name: String, value: String) = addMember("$name = %S", value)

  fun addEnumMember(name: String, value: Enum<*>): KotlinAnnotationSpecBuilder = addMember("$name = %M", value.asMemberName())

  fun addEnumMembers(name: String, vararg value: Enum<*>): KotlinAnnotationSpecBuilder {
    val members = value.map { it.asMemberName() }.asCodeBlock()
    return addMember(buildCodeBlock(format = "$name = %L", args = arrayOf(members)))
  }

  override fun builder(block: AnnotationSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(spec = delegate.build())
  override fun spec(): KotlinAnnotationSpec = build()
  override fun get(): AnnotationSpec = build().get()
}

typealias KotlinAnnotationSpecBuilderReceiver = KotlinAnnotationSpecBuilder.() -> Unit
