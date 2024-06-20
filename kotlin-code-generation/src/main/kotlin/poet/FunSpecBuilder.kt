package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.*
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

class FunSpecBuilder(
  override val builder: FunSpec.Builder
) : PoetSpecBuilder<FunSpecBuilder, FunSpec.Builder, FunSpec, FunSpecSupplier>,
  AnnotatableBuilder<FunSpecBuilder>,
  ContextReceivableBuilder<FunSpecBuilder>,
  DocumentableBuilder<FunSpecBuilder>,
  OriginatingElementsHolderBuilder<FunSpecBuilder> {
  companion object {
    fun FunSpec.Builder.wrap() = FunSpecBuilder(this)

    @JvmStatic
    fun builder(name: String): FunSpecBuilder = FunSpec.builder(name).wrap()

    @JvmStatic
    fun builder(memberName: MemberName): FunSpecBuilder = FunSpec.builder(memberName).wrap()

    @JvmStatic
    fun constructorBuilder(): FunSpecBuilder = FunSpec.constructorBuilder().wrap()

    @JvmStatic
    fun getterBuilder(): FunSpecBuilder = FunSpec.getterBuilder().wrap()

    @JvmStatic
    fun setterBuilder(): FunSpecBuilder = FunSpec.setterBuilder().wrap()

  }

  // AnnotatableBuilder
  override fun addAnnotation(annotationSpec: AnnotationSpec) = invoke { addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = invoke { addAnnotations(annotationSpecs) }

  // ContextReceiverBuilder
  @ExperimentalKotlinPoetApi
  override fun contextReceivers(receiverTypes: Iterable<TypeName>) = invoke { contextReceivers(receiverTypes) }

  @ExperimentalKotlinPoetApi
  override fun contextReceivers(vararg receiverTypes: TypeName) = invoke { contextReceivers(*receiverTypes) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = invoke { addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = invoke { addKdoc(block) }

  // OriginatingElementBuilder
  override fun addOriginatingElement(originatingElement: Element) = invoke { addOriginatingElement(originatingElement) }

  // FunSpecBuilder

  fun addModifiers(vararg modifiers: KModifier): FunSpecBuilder = invoke { addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): FunSpecBuilder = invoke { addModifiers(modifiers) }

  fun jvmModifiers(modifiers: Iterable<Modifier>) = builder.jvmModifiers(modifiers)

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): FunSpecBuilder = invoke { addTypeVariables(typeVariables) }

  fun addTypeVariable(typeVariable: TypeVariableName): FunSpecBuilder = invoke { addTypeVariable(typeVariable) }

  fun receiver(receiverType: TypeName): FunSpecBuilder = invoke { receiver(receiverType) }
  fun receiver(receiverType: TypeName, kdoc: CodeBlock): FunSpecBuilder = invoke { receiver(receiverType, kdoc) }

  fun receiver(receiverType: KClass<*>): FunSpecBuilder = invoke { receiver(receiverType) }
  fun receiver(receiverType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = invoke { receiver(receiverType, kdoc) }

  fun receiver(receiverType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = invoke { receiver(receiverType, kdoc, *args) }

  fun returns(returnType: TypeName): FunSpecBuilder = invoke { returns(returnType) }
  fun returns(returnType: TypeName, kdoc: CodeBlock): FunSpecBuilder = invoke { returns(returnType, kdoc) }

  fun returns(returnType: KClass<*>): FunSpecBuilder = invoke { returns(returnType) }
  fun returns(returnType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = invoke { returns(returnType, kdoc) }

  fun returns(returnType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = invoke { returns(returnType, kdoc, *args) }

  fun addParameters(parameterSpecs: Iterable<ParameterSpec>): FunSpecBuilder = invoke { addParameters(parameterSpecs) }
  fun addParameter(parameterSpec: ParameterSpec): FunSpecBuilder = invoke { addParameter(parameterSpec) }
  fun callThisConstructor(args: List<CodeBlock>): FunSpecBuilder = invoke { callThisConstructor(args) }

  fun callThisConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = invoke { callThisConstructor(args) }
  fun callThisConstructor(vararg args: String): FunSpecBuilder = invoke { callThisConstructor(*args) }
  fun callThisConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = invoke { callThisConstructor(*args) }
  fun callSuperConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = invoke { callSuperConstructor(args) }
  fun callSuperConstructor(args: List<CodeBlock>): FunSpecBuilder = invoke { callSuperConstructor(args) }
  fun callSuperConstructor(vararg args: String): FunSpecBuilder = invoke { callSuperConstructor(*args) }
  fun callSuperConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = invoke { callSuperConstructor(*args) }
  fun addParameter(name: String, type: TypeName, vararg modifiers: KModifier): FunSpecBuilder = invoke { addParameter(name, type, *modifiers) }
  fun addParameter(name: String, type: KClass<*>, vararg modifiers: KModifier): FunSpecBuilder = invoke { addParameter(name, type, *modifiers) }
  fun addParameter(name: String, type: TypeName, modifiers: Iterable<KModifier>): FunSpecBuilder = invoke { addParameter(name, type, modifiers) }
  fun addParameter(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): FunSpecBuilder = invoke { addParameter(name, type, modifiers) }
  fun addCode(format: String, vararg args: Any?): FunSpecBuilder = invoke { addCode(format, *args) }
  fun addNamedCode(format: String, args: Map<String, *>): FunSpecBuilder = invoke { addNamedCode(format, args) }
  fun addCode(codeBlock: CodeBlock): FunSpecBuilder = invoke { addCode(codeBlock) }
  fun addComment(format: String, vararg args: Any): FunSpecBuilder = invoke { addComment(format, *args) }

  fun beginControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = invoke { beginControlFlow(controlFlow, *args) }
  fun nextControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = invoke { nextControlFlow(controlFlow, *args) }
  fun endControlFlow(): FunSpecBuilder = invoke { endControlFlow() }
  fun addStatement(format: String, vararg args: Any): FunSpecBuilder = invoke { addStatement(format, *args) }

  fun clearBody(): FunSpecBuilder = invoke { clearBody() }

  override fun invoke(block: FunSpecBuilderReceiver): FunSpecBuilder = apply {
    builder.block()
  }

  override fun build(): FunSpec = builder.build()
}

interface FunSpecSupplier : PoetSpecSupplier<FunSpec>
typealias FunSpecBuilderReceiver = FunSpec.Builder.() -> Unit
