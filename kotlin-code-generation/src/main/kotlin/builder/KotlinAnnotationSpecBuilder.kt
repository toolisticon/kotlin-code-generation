package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildCodeBlock
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_KCLASS
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_LITERAL
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_MEMBER
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.format.FORMAT_STRING
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.name.asMemberName
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
import kotlin.reflect.KClass

/**
 * Builder for [KotlinAnnotationSpec].
 */
@ExperimentalKotlinPoetApi
//@Suppress(SUPPRESS_UNUSED)
class KotlinAnnotationSpecBuilder internal constructor(
  private val delegate: AnnotationSpecBuilder
) : BuilderSupplier<KotlinAnnotationSpec, AnnotationSpec>,
  KotlinTaggableBuilder<KotlinAnnotationSpecBuilder>,
  KotlinAnnotationSpecSupplier,
  DelegatingBuilder<KotlinAnnotationSpecBuilder, AnnotationSpecBuilderReceiver> {

  companion object {

    /**
     * Creates new builder.
     */
    fun builder(type: ClassName): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    /**
     * Creates new builder.
     */
    fun builder(type: ParameterizedTypeName): KotlinAnnotationSpecBuilder = KotlinAnnotationSpecBuilder(
      delegate = AnnotationSpecBuilder.builder(type)
    )

    /**
     * Creates new builder.
     */
    fun builder(type: KClass<out Annotation>): KotlinAnnotationSpecBuilder = builder(type.asClassName())

    /**
     * Creates new builder from spec.
     */
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

      fun enum(name: String, value: Enum<*>) = codeBlock("$name = $FORMAT_MEMBER", value.asMemberName())
      fun enums(name: String, vararg values: Enum<*>) = codeBlock("$name = $FORMAT_LITERAL", enumArray(*values).build())
    }

  }

  private var multiLine = false
  private val members: MutableList<CodeBlock> = mutableListOf()

  /**
   * If marked multiline all members become a new line.
   */
  fun multiLine() = apply { multiLine = true }

  /**
   * Add member to annotation.
   */
  fun addMember(codeBlock: CodeBlock): KotlinAnnotationSpecBuilder = apply { members.add(codeBlock) }

  /**
   * Add member to annotation.
   */
  fun addMember(format: String, vararg args: Any): KotlinAnnotationSpecBuilder = addMember(buildCodeBlock(format, *args))

  /**
   * Add member to annotation.
   */
  fun addNameMember(memberName: MemberName): KotlinAnnotationSpecBuilder = addMember("%M", memberName)

  /**
   * Add member to annotation.
   */
  fun addKClassMember(name: String, value: KClass<*>) = addMember(member.kclass(name, value))

  /**
   * Add member to annotation.
   */
  fun addKClassMembers(name: String, vararg values: KClass<*>) = addMember(member.kclasses(name, *values))

  /**
   * Add member to annotation.
   */
  fun addStringMember(name: String, value: String) = addMember(member.string(name, value))

  /**
   * Add member to annotation.
   */
  fun addStringMembers(name: String, vararg values: String) = addMember(member.strings(name, *values))

  /**
   * Add member to annotation.
   */
  fun addEnumMember(name: String, value: Enum<*>): KotlinAnnotationSpecBuilder = addMember(member.enum(name, value))

  /**
   * Add member to annotation.
   */
  fun addEnumMembers(name: String, vararg values: Enum<*>): KotlinAnnotationSpecBuilder = addMember(member.enums(name, *values))

  /**
   * Add member to annotation.
   */
  fun addNumberMember(name: String, value: Number): KotlinAnnotationSpecBuilder = addMember(member.number(name, value))

  /**
   * Add member to annotation.
   */
  fun addNumberMembers(name: String, vararg values: Number): KotlinAnnotationSpecBuilder = addMember(member.numbers(name, *values))

  /**
   * Remove all members.
   */
  fun clearMembers() = apply { members.clear() }


  override fun build(): KotlinAnnotationSpec {
    if (members.isNotEmpty()) {
      if (multiLine) {
        members.forEach(delegate::addMember)
      } else {
        delegate.addMember(CodeBlockArray.codeBlockArray(items = members.toTypedArray()).build())
      }
    }
    return KotlinAnnotationSpec(spec = delegate.build())
  }

  // <overrides>
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: AnnotationSpecBuilderReceiver) = apply { delegate.builder.block() }
  override fun get(): AnnotationSpec = build().get()
  override fun spec(): KotlinAnnotationSpec = build()
  // </overrides>
}

@ExperimentalKotlinPoetApi
typealias KotlinAnnotationSpecBuilderReceiver = KotlinAnnotationSpecBuilder.() -> Unit
