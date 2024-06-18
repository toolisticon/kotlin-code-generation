package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeSpec

class TypeSpecBuilder(
  override val builder: TypeSpec.Builder
) : PoetSpecBuilder<TypeSpecBuilder, TypeSpec.Builder, TypeSpec, TypeSpecSupplier> {
  companion object {
    fun TypeSpec.Builder.wrap() = TypeSpecBuilder(builder = this)

    @JvmStatic
    fun classBuilder(name: String): TypeSpecBuilder = TypeSpec.classBuilder(name).wrap()

    @JvmStatic
    fun classBuilder(className: ClassName): TypeSpecBuilder = classBuilder(className.simpleName)

    @JvmStatic
    fun objectBuilder(name: String): TypeSpecBuilder = TypeSpec.objectBuilder(name).wrap()

    @JvmStatic
    fun objectBuilder(className: ClassName): TypeSpecBuilder = objectBuilder(className.simpleName)

    @JvmStatic
    @JvmOverloads
    fun companionObjectBuilder(name: String? = null): TypeSpecBuilder = TypeSpec.companionObjectBuilder(name).wrap()

    @JvmStatic
    fun interfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.interfaceBuilder(name).wrap()

    @JvmStatic
    fun interfaceBuilder(className: ClassName): TypeSpecBuilder = interfaceBuilder(className.simpleName)

    @JvmStatic
    fun funInterfaceBuilder(name: String): TypeSpecBuilder = TypeSpec.funInterfaceBuilder(name).wrap()

    @JvmStatic
    fun funInterfaceBuilder(className: ClassName): TypeSpecBuilder = funInterfaceBuilder(className.simpleName)

    @JvmStatic
    fun enumBuilder(name: String): TypeSpecBuilder = TypeSpec.enumBuilder(name).wrap()

    @JvmStatic
    fun enumBuilder(className: ClassName): TypeSpecBuilder = enumBuilder(className.simpleName)

    @JvmStatic
    fun anonymousClassBuilder(): TypeSpecBuilder = TypeSpec.anonymousClassBuilder().wrap()

    @JvmStatic
    fun annotationBuilder(name: String): TypeSpecBuilder = TypeSpec.annotationBuilder(name).wrap()

    @JvmStatic
    fun annotationBuilder(className: ClassName): TypeSpecBuilder = annotationBuilder(className.simpleName)
  }


  override fun invoke(block: TypeSpecBuilderReceiver): TypeSpecBuilder = apply {
    builder.block()
  }

  override fun build(): TypeSpec = builder.build()
}

typealias TypeSpecBuilderReceiver = TypeSpec.Builder.() -> Unit
