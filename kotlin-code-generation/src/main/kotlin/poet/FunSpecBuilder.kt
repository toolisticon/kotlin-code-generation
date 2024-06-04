package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
@JvmInline
value class FunSpecBuilder(private val builder: FunSpec.Builder) : BuilderSupplier<FunSpec, FunSpec.Builder>,
  AnnotatableBuilder<FunSpecBuilder, FunSpec, FunSpec.Builder>,
  ContextReceivableBuilder<FunSpecBuilder, FunSpec, FunSpec.Builder>,
  DocumentableBuilder<FunSpecBuilder, FunSpec, FunSpec.Builder>,
  OriginatingElementsHolderBuilder<FunSpecBuilder, FunSpec, FunSpec.Builder>,
  TaggableBuilder<FunSpecBuilder, FunSpec, FunSpec.Builder> {

  val modifiers: MutableList<KModifier> get() = builder.modifiers
  val typeVariables: MutableList<TypeVariableName> get() = builder.typeVariables
  val parameters: MutableList<ParameterSpec> get() = builder.parameters

  fun addModifiers(vararg modifiers: KModifier): FunSpecBuilder = apply {
    builder.addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): FunSpecBuilder = apply {
    builder.addModifiers(modifiers)
  }

  fun jvmModifiers(modifiers: Iterable<Modifier>): FunSpecBuilder = apply {
    builder.jvmModifiers(modifiers)
  }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): FunSpecBuilder = apply {
    builder.addTypeVariables(typeVariables)
  }

  fun addTypeVariable(typeVariable: TypeVariableName): FunSpecBuilder = apply {
    builder.addTypeVariable(typeVariable)
  }

  fun receiver(receiverType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply {
    builder.receiver(receiverType, kdoc)
  }

  fun receiver(receiverType: Type, kdoc: CodeBlock): FunSpecBuilder = receiver(receiverType.asTypeName(), kdoc)

  fun receiver(receiverType: Type, kdoc: String, vararg args: Any): FunSpecBuilder = receiver(receiverType, CodeBlock.of(kdoc, args))

  fun receiver(receiverType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = receiver(receiverType.asTypeName(), kdoc)

  fun receiver(receiverType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = receiver(receiverType, CodeBlock.of(kdoc, args))

  fun returns(returnType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply {
    builder.returns(returnType, kdoc)
  }

  fun returns(returnType: Type, kdoc: CodeBlock): FunSpecBuilder = returns(returnType.asTypeName(), kdoc)

  fun returns(returnType: Type, kdoc: String, vararg args: Any): FunSpecBuilder = returns(returnType.asTypeName(), CodeBlock.of(kdoc, args))

  fun returns(returnType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = returns(returnType.asTypeName(), kdoc)

  fun returns(returnType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = returns(returnType.asTypeName(), CodeBlock.of(kdoc, args))

  fun addParameters(parameterSpecs: Iterable<ParameterSpec>): FunSpecBuilder = apply {
    builder.addParameters(parameterSpecs)
  }

  fun addParameter(parameterSpec: ParameterSpec): FunSpecBuilder = apply {
    builder.addParameter(parameterSpec)
  }

  fun callThisConstructor(args: List<CodeBlock>): FunSpecBuilder = apply {
    builder.callThisConstructor(args)
  }

  fun callThisConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply {
    builder.callThisConstructor(args.toList())
  }

  fun callThisConstructor(vararg args: String): FunSpecBuilder = apply {
    builder.callThisConstructor(args.map { CodeBlock.of(it) })
  }

  fun callThisConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply {
    builder.callThisConstructor(args.toList())
  }

  fun callSuperConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply {
    builder.callSuperConstructor(args.toList())
  }

  fun callSuperConstructor(args: List<CodeBlock>): FunSpecBuilder = apply {
    builder.callSuperConstructor(args)
  }

  fun callSuperConstructor(vararg args: String): FunSpecBuilder = apply {
    builder.callSuperConstructor(args.map { CodeBlock.of(it) })
  }

  fun callSuperConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply {
    builder.callSuperConstructor(args.toList())
  }

  fun addParameter(name: String, type: TypeName, vararg modifiers: KModifier): FunSpecBuilder =
    addParameter(ParameterSpec.builder(name, type, *modifiers).build())

  fun addParameter(name: String, type: Type, vararg modifiers: KModifier): FunSpecBuilder =
    addParameter(name, type.asTypeName(), *modifiers)

  fun addParameter(name: String, type: KClass<*>, vararg modifiers: KModifier): FunSpecBuilder =
    addParameter(name, type.asTypeName(), *modifiers)

  fun addParameter(name: String, type: TypeName, modifiers: Iterable<KModifier>): FunSpecBuilder =
    addParameter(ParameterSpec.builder(name, type, modifiers).build())

  fun addParameter(name: String, type: Type, modifiers: Iterable<KModifier>): FunSpecBuilder =
    addParameter(name, type.asTypeName(), modifiers)

  fun addParameter(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): FunSpecBuilder = addParameter(name, type.asTypeName(), modifiers)

  fun addCode(format: String, vararg args: Any?): FunSpecBuilder = apply {
    builder.addCode(format, *args)
  }

  fun addNamedCode(format: String, args: Map<String, *>): FunSpecBuilder = apply {
    builder.addNamedCode(format, args)
  }

  fun addCode(codeBlock: CodeBlock): FunSpecBuilder = apply {
    builder.addCode(codeBlock)
  }

  fun addComment(format: String, vararg args: Any): FunSpecBuilder = apply {
    builder.addComment("//·${format.replace(' ', '·')}\n", *args)
  }

  fun beginControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply {
    builder.beginControlFlow(controlFlow, *args)
  }

  fun nextControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply {
    builder.nextControlFlow(controlFlow, *args)
  }

  fun endControlFlow(): FunSpecBuilder = apply {
    builder.endControlFlow()
  }

  fun addStatement(format: String, vararg args: Any): FunSpecBuilder = apply {
    builder.addStatement(format, *args)
  }

  fun clearBody(): FunSpecBuilder = apply {
    builder.clearBody()
  }

  override fun build(): FunSpec = builder.build()
  override fun get(): FunSpec.Builder = builder
}
