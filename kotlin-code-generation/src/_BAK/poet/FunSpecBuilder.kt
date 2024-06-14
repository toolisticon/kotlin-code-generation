package io.toolisticon.kotlin.generation._BAK.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.FunSpec.Builder
import io.toolisticon.kotlin.generation.BuilderSupplier
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

typealias FunSpecBuilderReceiver = FunSpecBuilder.() -> Unit

@OptIn(ExperimentalKotlinPoetApi::class)
@JvmInline
value class FunSpecBuilder(private val builder: Builder) : BuilderSupplier<FunSpec, Builder>,
    AnnotatableBuilder<FunSpecBuilder, FunSpec, Builder>,
    ContextReceivableBuilder<FunSpecBuilder, FunSpec, Builder>,
    DocumentableBuilder<FunSpecBuilder, FunSpec, Builder>,
    KModifierHolderBuilder<FunSpecBuilder, FunSpec, Builder>,
    OriginatingElementsHolderBuilder<FunSpecBuilder, FunSpec, Builder>,
    TaggableBuilder<FunSpecBuilder, FunSpec, Builder>,
    TypeVariableHolderBuilder<FunSpecBuilder, FunSpec, Builder> {
  companion object {
    private fun Builder.wrap() = FunSpecBuilder(this)
    fun builder(name: String): FunSpecBuilder = FunSpec.builder(name).wrap()
    fun builder(memberName: MemberName): FunSpecBuilder = FunSpec.builder(memberName).wrap()

    fun constructorBuilder(): FunSpecBuilder = FunSpec.constructorBuilder().wrap()
    fun getterBuilder(): FunSpecBuilder = FunSpec.getterBuilder().wrap()
    fun setterBuilder(): FunSpecBuilder = FunSpec.setterBuilder().wrap()
  }

  override val modifiers: MutableList<KModifier> get() = builder.modifiers
  override val typeVariables: MutableList<TypeVariableName> get() = builder.typeVariables
  val parameters: MutableList<ParameterSpec> get() = builder.parameters

  fun addCode(codeBlock: CodeBlock): FunSpecBuilder = apply { builder.addCode(codeBlock) }
  fun addCode(format: String, vararg args: Any?): FunSpecBuilder = apply { builder.addCode(format, *args) }
  fun addComment(format: String, vararg args: Any): FunSpecBuilder = apply { builder.addComment("//·${format.replace(' ', '·')}\n", *args) }
  fun addNamedCode(format: String, args: Map<String, *>): FunSpecBuilder = apply { builder.addNamedCode(format, args) }
  fun addParameter(parameterSpec: ParameterSpec): FunSpecBuilder = apply { builder.addParameter(parameterSpec) }
  fun addParameter(name: String, type: TypeName, vararg modifiers: KModifier): FunSpecBuilder = addParameter(ParameterSpec.builder(name, type, *modifiers).build())
  fun addParameter(name: String, type: Type, vararg modifiers: KModifier): FunSpecBuilder = addParameter(name, type.asTypeName(), *modifiers)
  fun addParameter(name: String, type: KClass<*>, vararg modifiers: KModifier): FunSpecBuilder = addParameter(name, type.asTypeName(), *modifiers)
  fun addParameter(name: String, type: TypeName, modifiers: Iterable<KModifier>): FunSpecBuilder = addParameter(ParameterSpec.builder(name, type, modifiers).build())
  fun addParameter(name: String, type: Type, modifiers: Iterable<KModifier>): FunSpecBuilder = addParameter(name, type.asTypeName(), modifiers)
  fun addParameter(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): FunSpecBuilder = addParameter(name, type.asTypeName(), modifiers)
  fun addParameters(parameterSpecs: Iterable<ParameterSpec>): FunSpecBuilder = apply { builder.addParameters(parameterSpecs) }
  fun addStatement(format: String, vararg args: Any): FunSpecBuilder = apply { builder.addStatement(format, *args) }
  fun beginControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply { builder.beginControlFlow(controlFlow, *args) }
  fun callSuperConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply { builder.callSuperConstructor(args.toList()) }
  fun callSuperConstructor(args: List<CodeBlock>): FunSpecBuilder = apply { builder.callSuperConstructor(args) }
  fun callSuperConstructor(vararg args: String): FunSpecBuilder = apply { builder.callSuperConstructor(args.map { CodeBlock.of(it) }) }
  fun callSuperConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply { builder.callSuperConstructor(args.toList()) }
  fun callThisConstructor(args: List<CodeBlock>): FunSpecBuilder = apply { builder.callThisConstructor(args) }
  fun callThisConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply { builder.callThisConstructor(args.toList()) }
  fun callThisConstructor(vararg args: String): FunSpecBuilder = apply { builder.callThisConstructor(args.map { CodeBlock.of(it) }) }
  fun callThisConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply { builder.callThisConstructor(args.toList()) }
  fun clearBody(): FunSpecBuilder = apply { builder.clearBody() }
  fun endControlFlow(): FunSpecBuilder = apply { builder.endControlFlow() }
  fun jvmModifiers(modifiers: Iterable<Modifier>): FunSpecBuilder = apply { builder.jvmModifiers(modifiers) }
  fun nextControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply { builder.nextControlFlow(controlFlow, *args) }
  fun receiver(receiverType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply { builder.receiver(receiverType, kdoc) }
  fun receiver(receiverType: Type, kdoc: CodeBlock): FunSpecBuilder = receiver(receiverType.asTypeName(), kdoc)
  fun receiver(receiverType: Type, kdoc: String, vararg args: Any): FunSpecBuilder = receiver(receiverType, CodeBlock.of(kdoc, args))
  fun receiver(receiverType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = receiver(receiverType.asTypeName(), kdoc)
  fun receiver(receiverType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = receiver(receiverType, CodeBlock.of(kdoc, args))
  fun returns(returnType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply { builder.returns(returnType, kdoc) }
  fun returns(returnType: Type, kdoc: CodeBlock): FunSpecBuilder = returns(returnType.asTypeName(), kdoc)
  fun returns(returnType: Type, kdoc: String, vararg args: Any): FunSpecBuilder = returns(returnType.asTypeName(), CodeBlock.of(kdoc, args))
  fun returns(returnType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = returns(returnType.asTypeName(), kdoc)
  fun returns(returnType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = returns(returnType.asTypeName(), CodeBlock.of(kdoc, args))

  override fun build(): FunSpec = builder.build()
  override fun get(): Builder = builder
}
