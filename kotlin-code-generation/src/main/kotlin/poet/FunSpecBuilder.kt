package io.toolisticon.kotlin.generation.poet

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName

class FunSpecBuilder(
  override val builder: FunSpec.Builder
) : PoetSpecBuilder<FunSpecBuilder, FunSpec.Builder, FunSpec, FunSpecSupplier> {
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

  override fun invoke(block: FunSpecBuilderReceiver): FunSpecBuilder = apply {
    builder.block()
  }

  override fun build(): FunSpec = builder.build()
}

typealias FunSpecBuilderReceiver = FunSpec.Builder.() -> Unit
