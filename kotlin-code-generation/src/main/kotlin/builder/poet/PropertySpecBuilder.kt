package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass

@JvmInline
value class PropertySpecBuilder(private val builder: PropertySpec.Builder) : KotlinPoetBuilderSupplier<PropertySpec, PropertySpec.Builder> {

  val modifiers: MutableList<KModifier> get() = get().modifiers
  val typeVariables: MutableList<TypeVariableName> get() = get().typeVariables

  fun mutable(mutable: Boolean = true): PropertySpecBuilder = apply {
    get().mutable(mutable)
  }

  fun addModifiers(vararg modifiers: KModifier): PropertySpecBuilder = apply {
    get().addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): PropertySpecBuilder = apply {
    get().addModifiers(modifiers)
  }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): PropertySpecBuilder = apply {
    get().addTypeVariables(typeVariables)
  }

  fun addTypeVariable(typeVariable: TypeVariableName): PropertySpecBuilder = apply {
    get().addTypeVariable(typeVariable)
  }

  fun initializer(format: String, vararg args: Any?): PropertySpecBuilder =
    initializer(CodeBlock.of(format, *args))

  fun initializer(codeBlock: CodeBlock?): PropertySpecBuilder = apply {
    get().initializer(codeBlock)
  }

  fun delegate(format: String, vararg args: Any?): PropertySpecBuilder =
    delegate(CodeBlock.of(format, *args))

  fun delegate(codeBlock: CodeBlock): PropertySpecBuilder = apply {
    get().delegate(codeBlock)
  }

  fun getter(getter: FunSpec?): PropertySpecBuilder = apply {
    get().getter(getter)
  }

  fun setter(setter: FunSpec?): PropertySpecBuilder = apply {
    get().setter(setter)
  }

  fun receiver(receiverType: TypeName?): PropertySpecBuilder = apply {
    get().receiver(receiverType)
  }

  fun receiver(receiverType: KClass<*>): PropertySpecBuilder = receiver(receiverType.asTypeName())

  override fun build(): PropertySpec = get().build()

  override fun get(): PropertySpec.Builder = builder
}
