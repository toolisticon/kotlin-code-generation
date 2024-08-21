package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_LITERAL
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asMemberName
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.support.CodeBlockArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.enumArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.kclassArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.numberArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.stringArray
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
@Suppress(SUPPRESS_UNUSED)
class KotlinAnnotationSpecBuilder internal constructor(
  private val delegate: AnnotationSpecBuilder
) : BuilderSupplier<KotlinAnnotationSpec, AnnotationSpec>,
  KotlinAnnotationSpecSupplier,
  DelegatingBuilder<KotlinAnnotationSpecBuilder, AnnotationSpecBuilderReceiver> {

  companion object {
    fun builder(type: ClassName): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    fun builder(type: ParameterizedTypeName): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    fun builder(type: KClass<out Annotation>): KotlinAnnotationSpecBuilder = builder(type.asClassName())

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
  private fun addArrayMember(name: String, array: CodeBlockArray<*>): KotlinAnnotationSpecBuilder = addMember("$name = $FORMAT_LITERAL", array.build())

  fun addKClassMember(name: String, klass: KClass<*>) = addMember("$name = %T::class", klass)
  fun addKClassMembers(name: String, vararg klasses: KClass<*>) = addArrayMember(name = name, kclassArray(*klasses))

  fun addStringMember(name: String, value: String) = addMember("$name = %S", value)
  fun addStringMembers(name: String, vararg values: String) = addArrayMember(name = name, array = stringArray(*values))

  fun addEnumMember(name: String, value: Enum<*>): KotlinAnnotationSpecBuilder = addMember("$name = %M", value.asMemberName())
  fun addEnumMembers(name: String, vararg values: Enum<*>): KotlinAnnotationSpecBuilder = addArrayMember(name = name, enumArray(*values))

  fun addNumberMember(name: String, value: Number): KotlinAnnotationSpecBuilder = addMember("$name = %L", value)
  fun addNumberMembers(name: String, vararg values: Number): KotlinAnnotationSpecBuilder = addArrayMember(name = name, numberArray(*values))

  override fun builder(block: AnnotationSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(spec = delegate.build())
  override fun spec(): KotlinAnnotationSpec = build()
  override fun get(): AnnotationSpec = build().get()
}

@ExperimentalKotlinPoetApi
typealias KotlinAnnotationSpecBuilderReceiver = KotlinAnnotationSpecBuilder.() -> Unit
