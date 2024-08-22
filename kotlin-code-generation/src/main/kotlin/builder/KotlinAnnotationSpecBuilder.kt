package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_KCLASS
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_LITERAL
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_MEMBER
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.AnnotationSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.CodeBlockBuilder.Companion.codeBlock
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpecSupplier
import io.toolisticon.kotlin.generation.support.CodeBlockArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.enumArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.kclassArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.numberArray
import io.toolisticon.kotlin.generation.support.CodeBlockArray.Companion.stringArray
import io.toolisticon.kotlin.generation.support.SUPPRESS_CLASS_NAME
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

    @Suppress(SUPPRESS_CLASS_NAME)
    object member {
      fun string(name: String, value: String) = codeBlock("$name = $FORMAT_STRING", value)
      fun strings(name: String, vararg values: String) = codeBlock("$name = $FORMAT_LITERAL", stringArray(*values).build())

      fun number(name: String, value: Number) = codeBlock("$name = $FORMAT_LITERAL", value)
      fun numbers(name: String, vararg values: Number) = codeBlock("$name = $FORMAT_LITERAL", numberArray(*values).build())

      fun kclass(name: String, value: KClass<*>) = codeBlock("$name = $FORMAT_KCLASS", value)
      fun kclasses(name: String, vararg values: KClass<*>) = codeBlock("$name = $FORMAT_LITERAL", kclassArray(*values).build())

      fun enum(name: String, value: Enum<*>) = codeBlock("$name = $FORMAT_MEMBER", value)
      fun enums(name: String, vararg values: Enum<*>) = codeBlock("$name = $FORMAT_LITERAL", enumArray(*values).build())
    }
  }

  fun addMember(format: String, vararg args: Any): KotlinAnnotationSpecBuilder = apply { delegate.addMember(format, *args) }
  fun addMember(codeBlock: CodeBlock): KotlinAnnotationSpecBuilder = apply { delegate.addMember(codeBlock) }

  fun addMember(memberName: MemberName): KotlinAnnotationSpecBuilder = addMember("%M", memberName)
  private fun addArrayMember(name: String, array: CodeBlockArray<*>): KotlinAnnotationSpecBuilder = addMember("$name = $FORMAT_LITERAL", array.build())

  fun addKClassMember(name: String, value: KClass<*>) = addMember(member.kclass(name, value))
  fun addKClassMembers(name: String, vararg values: KClass<*>) = addMember(member.kclasses(name, *values))

  fun addStringMember(name: String, value: String) = addMember(member.string(name, value))
  fun addStringMembers(name: String, vararg values: String) = addMember(member.strings(name, *values))

  fun addEnumMember(name: String, value: Enum<*>): KotlinAnnotationSpecBuilder = addMember(member.enum(name, value))
  fun addEnumMembers(name: String, vararg values: Enum<*>): KotlinAnnotationSpecBuilder = addMember(member.enums(name, *values))

  fun addNumberMember(name: String, value: Number): KotlinAnnotationSpecBuilder = addMember(member.number(name, value))
  fun addNumberMembers(name: String, vararg values: Number): KotlinAnnotationSpecBuilder = addMember(member.numbers(name, *values))

  override fun builder(block: AnnotationSpecBuilderReceiver) = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(spec = delegate.build())
  override fun spec(): KotlinAnnotationSpec = build()
  override fun get(): AnnotationSpec = build().get()
}

@ExperimentalKotlinPoetApi
typealias KotlinAnnotationSpecBuilderReceiver = KotlinAnnotationSpecBuilder.() -> Unit
