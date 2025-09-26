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
import io.toolisticon.kotlin.generation.support.SUPPRESS_UNUSED
import kotlin.reflect.KClass

/**
 * Builder for [KotlinAnnotationSpec].
 */
@ExperimentalKotlinPoetApi
@Suppress(SUPPRESS_UNUSED)
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
  }

  @Suppress("ClassName")
  data object member {
    /**
     * Build a CodeBlock assigning a String value to an annotation member.
     * Example: name = "value"
     * @param name the annotation member name
     * @param value the String value
     * @return the CodeBlock representing the annotation member assignment
     */
    fun string(name: String, value: String) = codeBlock("$name = $FORMAT_STRING", value)

    /**
     * Build a CodeBlock assigning multiple String values to an annotation member array.
     * Example: names = ["a", "b"]
     * @param name the annotation member name
     * @param values the String values
     * @return the CodeBlock representing the annotation member array assignment
     */
    fun strings(name: String, vararg values: String) = codeBlock("$name = $FORMAT_LITERAL", stringArray(*values).build())

    /**
     * Build a CodeBlock assigning a numeric value to an annotation member.
     * Example: priority = 5
     * @param name the annotation member name
     * @param value the Number value
     * @return the CodeBlock representing the annotation member assignment
     */
    fun number(name: String, value: Number) = codeBlock("$name = $FORMAT_LITERAL", value)

    /**
     * Build a CodeBlock assigning multiple numeric values to an annotation member array.
     * Example: codes = [1, 2, 3]
     * @param name the annotation member name
     * @param values the Number values
     * @return the CodeBlock representing the annotation member array assignment
     */
    fun numbers(name: String, vararg values: Number) = codeBlock("$name = $FORMAT_LITERAL", numberArray(*values).build())

    /**
     * Build a CodeBlock assigning a KClass value to an annotation member.
     * Example: type = MyType::class
     * @param name the annotation member name
     * @param value the KClass value
     * @return the CodeBlock representing the annotation member assignment
     */
    fun kclass(name: String, value: KClass<*>) = codeBlock("$name = $FORMAT_KCLASS", value)

    /**
     * Build a CodeBlock assigning a ClassName value to an annotation member.
     * Example: type = MyType::class
     * @param name the annotation member name
     * @param value the ClassName value
     * @return the CodeBlock representing the annotation member assignment
     */
    fun kclass(name: String, value: ClassName) = codeBlock("$name = $FORMAT_KCLASS", value)

    /**
     * Build a CodeBlock assigning multiple KClass values to an annotation member array.
     * Example: types = [A::class, B::class]
     * @param name the annotation member name
     * @param values the KClass values
     * @return the CodeBlock representing the annotation member array assignment
     */
    fun kclasses(name: String, vararg values: KClass<*>) = codeBlock("$name = $FORMAT_LITERAL", kclassArray(*values).build())

    /**
     * Build a CodeBlock assigning multiple ClassName values to an annotation member array.
     * Example: types = [A::class, B::class]
     * @param name the annotation member name
     * @param values the ClassName values
     * @return the CodeBlock representing the annotation member array assignment
     */
    fun kclasses(name: String, vararg values: ClassName) = codeBlock("$name = $FORMAT_LITERAL", kclassArray(*values).build())

    /**
     * Build a CodeBlock assigning an Enum entry to an annotation member.
     * Example: mode = Mode.FAST
     * @param name the annotation member name
     * @param value the Enum constant
     * @return the CodeBlock representing the annotation member assignment
     */
    fun enum(name: String, value: Enum<*>) = codeBlock("$name = $FORMAT_MEMBER", value.asMemberName())

    /**
     * Build a CodeBlock assigning multiple Enum entries to an annotation member array.
     * Example: modes = [Mode.FAST, Mode.SAFE]
     * @param name the annotation member name
     * @param values the Enum constants
     * @return the CodeBlock representing the annotation member array assignment
     */
    fun enums(name: String, vararg values: Enum<*>) = codeBlock("$name = $FORMAT_LITERAL", enumArray(*values).build())
  }

  private var multiLine = false
  private val members: MutableList<CodeBlock> = mutableListOf()

  /**
   * If marked multiline all members become a new line.
   */
  fun multiLine() = apply { multiLine = true }

  /**
   * Add CodeBlock member to annotation.
   */
  fun addMember(codeBlock: CodeBlock): KotlinAnnotationSpecBuilder = apply { members.add(codeBlock) }

  /**
   * Add formatted member to annotation.
   */
  fun addMember(format: String, vararg args: Any): KotlinAnnotationSpecBuilder = addMember(buildCodeBlock(format, *args))

  /**
   * Add MemberName member to annotation.
   */
  fun addNameMember(memberName: MemberName): KotlinAnnotationSpecBuilder = addMember("%M", memberName)

  /**
   * Add KClass member to annotation.
   */
  fun addKClassMember(name: String, value: KClass<*>) = addMember(member.kclass(name, value))

  /**
   * Add ClassName member to annotation.
   */
  fun addKClassMember(name: String, value: ClassName) = addMember(member.kclass(name, value))

  /**
   * Add KClass members to annotation.
   */
  fun addKClassMembers(name: String, vararg values: KClass<*>) = addMember(member.kclasses(name, *values))

  /**
   * Add ClassName members to annotation.
   */
  fun addKClassMembers(name: String, vararg values: ClassName) = addMember(member.kclasses(name, *values))

  /**
   * Add String member to annotation.
   */
  fun addStringMember(name: String, value: String) = addMember(member.string(name, value))

  /**
   * Add String members to annotation.
   */
  fun addStringMembers(name: String, vararg values: String) = addMember(member.strings(name, *values))

  /**
   * Add Enum member to annotation.
   */
  fun addEnumMember(name: String, value: Enum<*>): KotlinAnnotationSpecBuilder = addMember(member.enum(name, value))

  /**
   * Add Enum members to annotation.
   */
  fun addEnumMembers(name: String, vararg values: Enum<*>): KotlinAnnotationSpecBuilder = addMember(member.enums(name, *values))

  /**
   * Add Number member to annotation.
   */
  fun addNumberMember(name: String, value: Number): KotlinAnnotationSpecBuilder = addMember(member.number(name, value))

  /**
   * Add Number members to annotation.
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

  // region [overrides]
  override fun addTag(type: KClass<*>, tag: Any?) = builder { this.tag(type, tag) }
  override fun builder(block: AnnotationSpecBuilderReceiver) = apply { delegate.builder.block() }
  override fun get(): AnnotationSpec = build().get()
  override fun spec(): KotlinAnnotationSpec = build()
  // endregion [overrides]
}

@ExperimentalKotlinPoetApi
typealias KotlinAnnotationSpecBuilderReceiver = KotlinAnnotationSpecBuilder.() -> Unit
