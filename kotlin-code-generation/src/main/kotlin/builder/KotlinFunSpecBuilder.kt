package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.FunSpecBuilderReceiver
import io.toolisticon.kotlin.generation.poet.KDoc
import io.toolisticon.kotlin.generation.spec.KotlinFunSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinParameterSpecSupplier
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

@ExperimentalKotlinPoetApi
class KotlinFunSpecBuilder internal constructor(
  private val delegate: FunSpecBuilder
) : BuilderSupplier<KotlinFunSpec, FunSpec>,
  KotlinFunSpecSupplier,
  DelegatingBuilder<KotlinFunSpecBuilder, FunSpecBuilderReceiver>,
  KotlinDocumentableBuilder<KotlinFunSpecBuilder> {

  companion object {
    fun builder(name: String): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.builder(name)
    )

    fun builder(memberName: MemberName): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.builder(memberName)
    )

    fun constructorBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.constructorBuilder()
    )

    fun getterBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.getterBuilder()
    )

    fun setterBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.setterBuilder()
    )

    fun builder(spec: KotlinFunSpec) = builder(spec.get())

    fun builder(spec: FunSpec) = KotlinFunSpecBuilder(delegate = spec.toBuilder().wrap())
  }

  fun addParameter(parameter: KotlinParameterSpecSupplier) = builder { this.addParameter(parameter.get()) }

  override fun addKdoc(kdoc: KDoc): KotlinFunSpecBuilder = apply {
    delegate.addKdoc(kdoc.get())
  }

  fun addKdoc(format: String, vararg args: Any) = builder { addKdoc(format, *args) }
  fun addKdoc(block: CodeBlock) = builder { addKdoc(block) }

  fun addAnnotation(annotationSpec: AnnotationSpecSupplier) = builder { this.addAnnotation(annotationSpec.get()) }
  fun contextReceivers(vararg receiverTypes: TypeName) = builder { this.contextReceivers(*receiverTypes) }

  fun addOriginatingElement(originatingElement: Element) = builder { this.addOriginatingElement(originatingElement) }

  fun addModifiers(vararg modifiers: KModifier) = builder { this.addModifiers(*modifiers) }

  fun jvmModifiers(modifiers: Iterable<Modifier>) = builder { this.jvmModifiers(modifiers) }

  fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) = builder { this.addTypeVariables(typeVariables) }

  fun addTypeVariable(typeVariable: TypeVariableName) = builder { this.addTypeVariable(typeVariable) }

  fun receiver(receiverType: TypeName) = builder { this.receiver(receiverType) }
  fun receiver(receiverType: TypeName, kdoc: CodeBlock) = builder { this.receiver(receiverType, kdoc) }

  fun receiver(receiverType: KClass<*>) = builder { this.receiver(receiverType) }
  fun receiver(receiverType: KClass<*>, kdoc: CodeBlock) = builder { this.receiver(receiverType, kdoc) }

  fun receiver(receiverType: KClass<*>, kdoc: String, vararg args: Any) = builder { this.receiver(receiverType, kdoc, *args) }

  fun returns(returnType: TypeName) = builder { this.returns(returnType) }
  fun returns(returnType: TypeName, kdoc: CodeBlock) = builder { this.returns(returnType, kdoc) }

  fun returns(returnType: KClass<*>) = builder { this.returns(returnType) }
  fun returns(returnType: KClass<*>, kdoc: CodeBlock) = builder { this.returns(returnType, kdoc) }

  fun returns(returnType: KClass<*>, kdoc: String, vararg args: Any) = builder { this.returns(returnType, kdoc, *args) }

  fun addParameters(parameterSpecs: Iterable<ParameterSpec>) = builder { this.addParameters(parameterSpecs) }
  fun addParameter(parameterSpec: ParameterSpec) = builder { this.addParameter(parameterSpec) }
  fun callThisConstructor(args: List<CodeBlock>) = builder { this.callThisConstructor(args) }

  fun callThisConstructor(args: Iterable<CodeBlock>) = builder { this.callThisConstructor(args) }
  fun callThisConstructor(vararg args: String) = builder { this.callThisConstructor(*args) }
  fun callThisConstructor(vararg args: CodeBlock = emptyArray()) = builder { this.callThisConstructor(*args) }
  fun callSuperConstructor(args: Iterable<CodeBlock>) = builder { this.callSuperConstructor(args) }
  fun callSuperConstructor(args: List<CodeBlock>) = builder { this.callSuperConstructor(args) }
  fun callSuperConstructor(vararg args: String) = builder { this.callSuperConstructor(*args) }
  fun callSuperConstructor(vararg args: CodeBlock = emptyArray()) = builder { this.callSuperConstructor(*args) }
  fun addParameter(name: String, type: TypeName, vararg modifiers: KModifier) = builder { this.addParameter(name, type, *modifiers) }
  fun addParameter(name: String, type: KClass<*>, vararg modifiers: KModifier) = builder { this.addParameter(name, type, *modifiers) }
  fun addParameter(name: String, type: TypeName, modifiers: Iterable<KModifier>) = builder { this.addParameter(name, type, modifiers) }
  fun addParameter(name: String, type: KClass<*>, modifiers: Iterable<KModifier>) = builder { this.addParameter(name, type, modifiers) }
  fun addCode(format: String, vararg args: Any?) = builder { this.addCode(format, *args) }
  fun addNamedCode(format: String, args: Map<String, *>) = builder { this.addNamedCode(format, args) }
  fun addCode(codeBlock: CodeBlock) = builder { this.addCode(codeBlock) }
  fun addComment(format: String, vararg args: Any) = builder { this.addComment(format, *args) }

  fun beginControlFlow(controlFlow: String, vararg args: Any) = builder { this.beginControlFlow(controlFlow, *args) }
  fun nextControlFlow(controlFlow: String, vararg args: Any) = builder { this.nextControlFlow(controlFlow, *args) }
  fun endControlFlow() = builder { this.endControlFlow() }
  fun addStatement(format: String, vararg args: Any) = builder { this.addStatement(format, *args) }


  override fun builder(block: FunSpecBuilderReceiver): KotlinFunSpecBuilder = apply {
    delegate.builder.block()
  }

  override fun build(): KotlinFunSpec = KotlinFunSpec(spec = delegate.build())
  override fun spec(): KotlinFunSpec = build()
  override fun get(): FunSpec = build().get()
}

@ExperimentalKotlinPoetApi
typealias KotlinFunSpecBuilderReceiver = KotlinFunSpecBuilder.() -> Unit
