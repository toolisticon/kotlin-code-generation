package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.buildConstructorProperty
import io.toolisticon.kotlin.generation.spec.KotlinConstructorPropertySpecSupplier
import kotlin.reflect.KClass


interface DelegatingBuilder<SELF, RECEIVER> {
  fun builder(block: RECEIVER) : SELF
}

interface ConstructorPropertySupport<SELF> {

  fun addConstructorProperty(spec: KotlinConstructorPropertySpecSupplier) : SELF

  fun addConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )

  fun addConstructorProperty(name: String, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = addConstructorProperty(
    buildConstructorProperty(name, type, block)
  )
}
