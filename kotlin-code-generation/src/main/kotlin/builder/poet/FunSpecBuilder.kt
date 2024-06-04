package io.toolisticon.kotlin.generation.builder.poet

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass


@ExperimentalKotlinPoetApi
@JvmInline
value class FunSpecBuilder(private val builder: FunSpec.Builder) : KotlinPoetBuilderSupplier<FunSpec, FunSpec.Builder>,
  AnnotatableBuilder<FunSpec, FunSpec.Builder>,
  ContextReceivableBuilder<FunSpec, FunSpec.Builder>,
  DocumentableBuilder<FunSpec, FunSpec.Builder>,
  OriginatingElementsHolderBuilder<FunSpec, FunSpec.Builder>,
  TaggableBuilder<FunSpec, FunSpec.Builder> {

  val modifiers: MutableList<KModifier> get() = get().modifiers
  val typeVariables: MutableList<TypeVariableName> get() = get().typeVariables
  val parameters: MutableList<ParameterSpec> get() = get().parameters

  fun addModifiers(vararg modifiers: KModifier): FunSpecBuilder = apply {
    get().addModifiers(*modifiers)
  }

  fun addModifiers(modifiers: Iterable<KModifier>): FunSpecBuilder = apply {
    get().addModifiers(modifiers)
  }

  fun jvmModifiers(modifiers: Iterable<Modifier>): FunSpecBuilder = apply {
    get().jvmModifiers(modifiers)
  }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): FunSpecBuilder = apply {
    get().addTypeVariables(typeVariables)
  }

  fun addTypeVariable(typeVariable: TypeVariableName): FunSpecBuilder = apply {
    get().addTypeVariable(typeVariable)
  }

  fun receiver(receiverType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply {
    get().receiver(receiverType, kdoc)
  }

  fun receiver(receiverType: Type, kdoc: CodeBlock): FunSpecBuilder = receiver(receiverType.asTypeName(), kdoc)

  fun receiver(receiverType: Type, kdoc: String, vararg args: Any): FunSpecBuilder = receiver(receiverType, CodeBlock.of(kdoc, args))

  fun receiver(receiverType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = receiver(receiverType.asTypeName(), kdoc)

  fun receiver(receiverType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = receiver(receiverType, CodeBlock.of(kdoc, args))

  fun returns(returnType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply {
    get().returns(returnType, kdoc)
  }

  fun returns(returnType: Type, kdoc: CodeBlock): FunSpecBuilder = returns(returnType.asTypeName(), kdoc)

  fun returns(returnType: Type, kdoc: String, vararg args: Any): FunSpecBuilder = returns(returnType.asTypeName(), CodeBlock.of(kdoc, args))

  fun returns(returnType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = returns(returnType.asTypeName(), kdoc)

  fun returns(returnType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = returns(returnType.asTypeName(), CodeBlock.of(kdoc, args))

  fun addParameters(parameterSpecs: Iterable<ParameterSpec>): FunSpecBuilder = apply {
    for (parameterSpec in parameterSpecs) {
      addParameter(parameterSpec)
    }
  }

  fun addParameter(parameterSpec: ParameterSpec): FunSpecBuilder = apply {
    parameters += parameterSpec
  }

  fun callThisConstructor(args: List<CodeBlock>): FunSpecBuilder = apply {
    get().callThisConstructor(args)
  }

  fun callThisConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply {
    get().callThisConstructor(args.toList())
  }

  fun callThisConstructor(vararg args: String): FunSpecBuilder = apply {
    get().callThisConstructor(args.map { CodeBlock.of(it) })
  }

  fun callThisConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply {
    get().callThisConstructor(args.toList())
  }

  fun callSuperConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply {
    get().callSuperConstructor(args.toList())
  }

  fun callSuperConstructor(args: List<CodeBlock>): FunSpecBuilder = apply {
    get().callSuperConstructor(args)
  }

  fun callSuperConstructor(vararg args: String): FunSpecBuilder = apply {
    get().callSuperConstructor(args.map { CodeBlock.of(it) })
  }

  fun callSuperConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply {
    get().callSuperConstructor(args.toList())
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
    get().addCode(format, *args)
  }

  fun addNamedCode(format: String, args: Map<String, *>): FunSpecBuilder = apply {
    get().addNamedCode(format, args)
  }

  fun addCode(codeBlock: CodeBlock): FunSpecBuilder = apply {
    get().addCode(codeBlock)
  }

  fun addComment(format: String, vararg args: Any): FunSpecBuilder = apply {
    get().addComment("//·${format.replace(' ', '·')}\n", *args)
  }

  fun beginControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply {
    get().beginControlFlow(controlFlow, *args)
  }

  fun nextControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply {
    get().nextControlFlow(controlFlow, *args)
  }

  fun endControlFlow(): FunSpecBuilder = apply {
    get().endControlFlow()
  }

  fun addStatement(format: String, vararg args: Any): FunSpecBuilder = apply {
    get().addStatement(format, *args)
  }

  fun clearBody(): FunSpecBuilder = apply {
    get().clearBody()
  }

  override fun build(): FunSpec = get().build()
  override fun get(): FunSpec.Builder = builder
}
