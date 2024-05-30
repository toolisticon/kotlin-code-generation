package io.toolisticon.kotlin.generation.builder

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.AnnotationSpec.Builder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.spec.AnnotationSpecSupplier
import io.toolisticon.kotlin.generation.spec.KotlinAnnotationSpec
import kotlin.reflect.KClass

class KotlinAnnotationBuilder internal constructor(
  private val typeName: TypeName,
  delegate: Builder
) : KotlinPoetSpecBuilder<KotlinAnnotationBuilder, KotlinAnnotationSpec, AnnotationSpec, Builder>(
  delegate = delegate
), AnnotationSpecSupplier {

  @Suppress(CLASS_NAME)
  object builder : ToKotlinPoetSpecBuilder<KotlinAnnotationSpec, KotlinAnnotationBuilder> {

    operator fun invoke(type: KClass<out Annotation>, block: AnnotationSpecBuilderReceiver = {}): KotlinAnnotationBuilder = invoke(
      typeName = type.asClassName(),
      block = block
    )

    operator fun invoke(typeName: ClassName, block: AnnotationSpecBuilderReceiver = {}): KotlinAnnotationBuilder = KotlinAnnotationBuilder(
      typeName = typeName,
      delegate = AnnotationSpec.builder(typeName)
    ).invoke(block)

    operator fun invoke(typeName: ParameterizedTypeName, block: AnnotationSpecBuilderReceiver = {}): KotlinAnnotationBuilder = KotlinAnnotationBuilder(
      typeName = typeName,
      delegate = AnnotationSpec.builder(typeName)
    ).invoke(block)

    override fun invoke(spec: KotlinAnnotationSpec): KotlinAnnotationBuilder = KotlinAnnotationBuilder(
      typeName = spec.typeName,
      delegate = spec.get().toBuilder()
    )
  }

  fun addMember(format: String, vararg args: Any): KotlinAnnotationBuilder = apply {
    delegate.addMember(format, *args)
  }

  fun addMember(codeBlock: CodeBlock): KotlinAnnotationBuilder = apply {
    delegate.addMember(codeBlock)
  }

  fun addKClassMember(name: String, klass: KClass<*>) = addMember("$name = %T::class", klass)

  fun addStringMember(name: String, value: String) = addMember("$name = %S", value)

  override fun build(): KotlinAnnotationSpec = KotlinAnnotationSpec(
    spec = delegate.build()
  )

  override fun get(): AnnotationSpec = build().get()

}
