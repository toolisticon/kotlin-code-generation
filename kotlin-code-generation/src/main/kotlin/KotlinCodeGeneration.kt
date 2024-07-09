package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.classBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.fileBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.funBuilder
import io.toolisticon.kotlin.generation.builder.*
import io.toolisticon.kotlin.generation.poet.FormatSpecifier.asCodeBlock
import io.toolisticon.kotlin.generation.spec.*
import io.toolisticon.kotlin.generation.support.SuppressAnnotation.Companion.CLASS_NAME
import kotlin.reflect.KClass

object KotlinCodeGeneration {

  inline fun buildAnnotation(type: KClass<*>, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = buildAnnotation(type.asClassName(), block)
  inline fun buildAnnotation(className: ClassName, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = annotationBuilder(className).also(block).build()
  inline fun buildAnnotationClass(className: ClassName, block: KotlinAnnotationClassSpecBuilderReceiver = {}): KotlinAnnotationClassSpec = annotationClassBuilder(className).also(block).build()

  inline fun buildClass(className: ClassName, block: KotlinClassSpecBuilderReceiver = {}) = classBuilder(className).also(block).build()

  fun buildCodeBlock(format: String, vararg args: Any?) = CodeBlock.of(format, *args)
  inline fun buildCodeBlock(builderAction: CodeBlock.Builder.() -> Unit): CodeBlock = CodeBlock.builder().apply(builderAction).build()

  inline fun buildConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = constructorPropertyBuilder(name, type).also(block).build()
  inline fun buildConstructorProperty(name: String, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = KotlinCodeGeneration.buildConstructorProperty(name, type.asTypeName(), block)

  inline fun buildDataClass(className: ClassName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec = KotlinDataClassSpecBuilder.builder(className).also(block).build()

  inline fun buildFile(className: ClassName, block: KotlinFileSpecBuilderReceiver = {}): KotlinFileSpec = fileBuilder(className).also(block).build()

  inline fun buildFun(name: String, block: KotlinFunSpecBuilderReceiver = {}): KotlinFunSpec = funBuilder(name).also(block).build()

  inline fun buildValueClass(className: ClassName, block: KotlinValueClassSpecBuilderReceiver = {}): KotlinValueClassSpec = KotlinValueClassSpecBuilder.builder(className).also(block).build()

  fun toFileSpec(spec: KotlinValueClassSpecSupplier): KotlinFileSpec = spec.spec().let {
    KotlinFileSpecBuilder.builder(it.className).addType(it).build()
  }


  @Suppress(CLASS_NAME)
  object builder {
    fun annotationBuilder(type: ClassName) = KotlinAnnotationSpecBuilder.builder(type)
    fun annotationClassBuilder(className: ClassName) = KotlinAnnotationClassSpecBuilder.builder(className)
    fun classBuilder(className: ClassName) = KotlinClassSpecBuilder.builder(className);
    fun constructorPropertyBuilder(name: String, type: TypeName) = KotlinConstructorPropertySpecBuilder.builder(name, type)
    fun dataClassBuilder(className: ClassName) = KotlinDataClassSpecBuilder.builder(className);
    fun fileBuilder(className: ClassName) = KotlinFileSpecBuilder.builder(className)
    fun funBuilder(name: String) = KotlinFunSpecBuilder.builder(name)
    fun valueClassBuilder(className: ClassName) = KotlinValueClassSpecBuilder.builder(className)
  }

  @Suppress(CLASS_NAME)
  object typeSpec {

    fun TypeSpec.hasModifier(modifier: KModifier) = this.modifiers.contains(modifier)

    val TypeSpec.isDataClass: Boolean get() = hasModifier(KModifier.DATA)
    val TypeSpec.isValueClass: Boolean get() = hasModifier(KModifier.VALUE)
  }

  @Suppress(CLASS_NAME)
  object name {
    fun Collection<MemberName>.asCodeBlock(): CodeBlock = this.map { it.asCodeBlock() }.joinToCode(prefix = "[", suffix = "]")

    fun Enum<*>.asMemberName(): MemberName = this::class.asClassName().member(this.name)

    operator fun ClassName.plus(suffix: String?): ClassName = ClassName(this.packageName, this.simpleNames)

    fun TypeName.nullable(nullable: Boolean = true): TypeName = this.copy(nullable = nullable)
  }

}
