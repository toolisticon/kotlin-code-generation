package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import io.toolisticon.kotlin.generation.BuilderSupplier
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder
import io.toolisticon.kotlin.generation.poet.FunSpecBuilder.Companion.wrap
import io.toolisticon.kotlin.generation.poet.FunSpecBuilderReceiver
import io.toolisticon.kotlin.generation.spec.KotlinFunSpec
import io.toolisticon.kotlin.generation.spec.KotlinFunSpecSupplier

class KotlinFunSpecBuilder internal constructor(
  private val delegate: FunSpecBuilder
) : BuilderSupplier<KotlinFunSpec, FunSpec>,
  KotlinFunSpecSupplier,
  DelegatingBuilder<KotlinFunSpecBuilder, FunSpecBuilderReceiver> {

  companion object {
    @JvmStatic
    fun builder(
      name: String
    ): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.builder(name)
    )

    @JvmStatic
    fun builder(
      memberName: MemberName
    ): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.builder(memberName)
    )

    @JvmStatic
    fun constructorBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.constructorBuilder()
    )

    @JvmStatic
    fun getterBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.getterBuilder()
    )

    @JvmStatic
    fun setterBuilder(): KotlinFunSpecBuilder = KotlinFunSpecBuilder(
      delegate = FunSpecBuilder.setterBuilder()
    )


    @JvmStatic
    fun builder(spec: KotlinFunSpec) = builder(spec.get())

    @JvmStatic
    fun builder(spec: FunSpec) = KotlinFunSpecBuilder(delegate = spec.toBuilder().wrap())
  }

  override fun builder(block: FunSpecBuilderReceiver): KotlinFunSpecBuilder = apply {
    delegate { block() }
  }

  override fun build(): KotlinFunSpec = KotlinFunSpec(spec = delegate.build())
  override fun spec(): KotlinFunSpec = build()
  override fun get(): FunSpec = build().get()
}
