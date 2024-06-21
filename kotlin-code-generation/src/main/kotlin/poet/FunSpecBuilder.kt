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
  override fun addAnnotation(annotationSpec: AnnotationSpec) = apply { builder.addAnnotation(annotationSpec) }
  override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) = apply { builder.addAnnotations(annotationSpecs) }

  // ContextReceiverBuilder
  @ExperimentalKotlinPoetApi
  override fun contextReceivers(receiverTypes: Iterable<TypeName>) = apply { builder.contextReceivers(receiverTypes) }

  @ExperimentalKotlinPoetApi
  override fun contextReceivers(vararg receiverTypes: TypeName) = apply { builder.contextReceivers(*receiverTypes) }

  // DocumentableBuilder
  override fun addKdoc(format: String, vararg args: Any) = apply { builder.addKdoc(format, *args) }
  override fun addKdoc(block: CodeBlock) = apply { builder.addKdoc(block) }

  // OriginatingElementBuilder
  override fun addOriginatingElement(originatingElement: Element) = apply { builder.addOriginatingElement(originatingElement) }

  // FunSpecBuilder

  fun addModifiers(vararg modifiers: KModifier): FunSpecBuilder = apply { builder.addModifiers(*modifiers) }
  fun addModifiers(modifiers: Iterable<KModifier>): FunSpecBuilder = apply { builder.addModifiers(modifiers) }

  fun jvmModifiers(modifiers: Iterable<Modifier>) = builder.jvmModifiers(modifiers)

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>): FunSpecBuilder = apply { builder.addTypeVariables(typeVariables) }

  fun addTypeVariable(typeVariable: TypeVariableName): FunSpecBuilder = apply { builder.addTypeVariable(typeVariable) }

  fun receiver(receiverType: TypeName): FunSpecBuilder = apply { builder.receiver(receiverType) }
  fun receiver(receiverType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply { builder.receiver(receiverType, kdoc) }

  fun receiver(receiverType: KClass<*>): FunSpecBuilder = apply { builder.receiver(receiverType) }
  fun receiver(receiverType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = apply { builder.receiver(receiverType, kdoc) }

  fun receiver(receiverType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = apply { builder.receiver(receiverType, kdoc, *args) }

  fun returns(returnType: TypeName): FunSpecBuilder = apply { builder.returns(returnType) }
  fun returns(returnType: TypeName, kdoc: CodeBlock): FunSpecBuilder = apply { builder.returns(returnType, kdoc) }

  fun returns(returnType: KClass<*>): FunSpecBuilder = apply { builder.returns(returnType) }
  fun returns(returnType: KClass<*>, kdoc: CodeBlock): FunSpecBuilder = apply { builder.returns(returnType, kdoc) }

  fun returns(returnType: KClass<*>, kdoc: String, vararg args: Any): FunSpecBuilder = apply { builder.returns(returnType, kdoc, *args) }

  fun addParameters(parameterSpecs: Iterable<ParameterSpec>): FunSpecBuilder = apply { builder.addParameters(parameterSpecs) }
  fun addParameter(parameterSpec: ParameterSpec): FunSpecBuilder = apply { builder.addParameter(parameterSpec) }
  fun callThisConstructor(args: List<CodeBlock>): FunSpecBuilder = apply { builder.callThisConstructor(args) }

  fun callThisConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply { builder.callThisConstructor(args) }
  fun callThisConstructor(vararg args: String): FunSpecBuilder = apply { builder.callThisConstructor(*args) }
  fun callThisConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply { builder.callThisConstructor(*args) }
  fun callSuperConstructor(args: Iterable<CodeBlock>): FunSpecBuilder = apply { builder.callSuperConstructor(args) }
  fun callSuperConstructor(args: List<CodeBlock>): FunSpecBuilder = apply { builder.callSuperConstructor(args) }
  fun callSuperConstructor(vararg args: String): FunSpecBuilder = apply { builder.callSuperConstructor(*args) }
  fun callSuperConstructor(vararg args: CodeBlock = emptyArray()): FunSpecBuilder = apply { builder.callSuperConstructor(*args) }
  fun addParameter(name: String, type: TypeName, vararg modifiers: KModifier): FunSpecBuilder = apply { builder.addParameter(name, type, *modifiers) }
  fun addParameter(name: String, type: KClass<*>, vararg modifiers: KModifier): FunSpecBuilder = apply { builder.addParameter(name, type, *modifiers) }
  fun addParameter(name: String, type: TypeName, modifiers: Iterable<KModifier>): FunSpecBuilder = apply { builder.addParameter(name, type, modifiers) }
  fun addParameter(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): FunSpecBuilder = apply { builder.addParameter(name, type, modifiers) }
  fun addCode(format: String, vararg args: Any?): FunSpecBuilder = apply { builder.addCode(format, *args) }
  fun addNamedCode(format: String, args: Map<String, *>): FunSpecBuilder = apply { builder.addNamedCode(format, args) }
  fun addCode(codeBlock: CodeBlock): FunSpecBuilder = apply { builder.addCode(codeBlock) }
  fun addComment(format: String, vararg args: Any): FunSpecBuilder = apply { builder.addComment(format, *args) }

  fun beginControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply { builder.beginControlFlow(controlFlow, *args) }
  fun nextControlFlow(controlFlow: String, vararg args: Any): FunSpecBuilder = apply { builder.nextControlFlow(controlFlow, *args) }
  fun endControlFlow(): FunSpecBuilder = apply { builder.endControlFlow() }
  fun addStatement(format: String, vararg args: Any): FunSpecBuilder = apply { builder.addStatement(format, *args) }

  fun clearBody(): FunSpecBuilder = apply { builder.clearBody() }

  override fun build(): FunSpec = builder.build()
}

interface FunSpecSupplier : PoetSpecSupplier<FunSpec>
typealias FunSpecBuilderReceiver = FunSpec.Builder.() -> Unit
