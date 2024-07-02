package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.MemberName.Companion.member
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.fileBuilder
import io.toolisticon.kotlin.generation.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.*
import io.toolisticon.kotlin.generation.spec.*
import jakarta.annotation.Generated
import java.time.Instant
import kotlin.reflect.KClass

object KotlinCodeGeneration {

  fun buildAnnotation(type: KClass<*>, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = buildAnnotation(type.asClassName(), block)
  fun buildAnnotation(className: ClassName, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = annotationBuilder(className).also(block).build()
  fun buildAnnotationClass(className: ClassName, block: KotlinAnnotationClassSpecBuilderReceiver = {}): KotlinAnnotationClassSpec = annotationClassBuilder(className).also(block).build()
  fun buildCodeBlock(format: String, vararg args: Any?) = CodeBlock.of(format, *args)
  inline fun buildCodeBlock(builderAction: CodeBlock.Builder.() -> Unit): CodeBlock = CodeBlock.builder().apply(builderAction).build()
  fun buildConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = constructorPropertyBuilder(name, type).also(block).build()
  fun buildConstructorProperty(name: String, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = KotlinCodeGeneration.buildConstructorProperty(name, type.asTypeName(), block)
  fun buildDataClass(className: ClassName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec = KotlinDataClassSpecBuilder.builder(className).also(block).build()
  fun buildFile(className: ClassName, block: KotlinFileSpecBuilderReceiver = {}): KotlinFileSpec = fileBuilder(className).also(block).build()
  fun buildValueClass(className: ClassName, block: KotlinValueClassSpecBuilderReceiver): KotlinValueClassSpec = KotlinValueClassSpecBuilder.builder(className).also(block).build()

  fun toFileSpec(spec: KotlinValueClassSpecSupplier): KotlinFileSpec = spec.spec().let {
    KotlinFileSpecBuilder.builder(it.className).addType(it).build()
  }
//  val annotationBuilder = KotlinAnnotationBuilder.builder
//  val constructorPropertyBuilder = KotlinConstructorPropertyBuilder.builder
//  val dataClassBuilder = KotlinDataClassSpecBuilder.builder
//  val parameterBuilder = KotlinParameterBuilder.builder
//  val propertyBuilder = KotlinPropertyBuilder.builder
//  val valueClassBuilder = KotlinValueClassBuilder.builder

//  inline fun buildAnnotation(type: ClassName, block: KotlinAnnotationSpecBuilder.() -> Unit) = KotlinAnnotationSpecBuilder.builder(type).also(block).build()
//  inline fun buildDataClass(className: ClassName, block: io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.() -> Unit): KotlinDataClassSpec =
//    io.toolisticon.kotlin.generation._BAK.KotlinDataClassBuilder.builder(className).also(block).build()
//
//  inline fun buildConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertyBuilder.() -> Unit) = KotlinConstructorPropertyBuilder.builder(name, type).also(block).build()
//  inline fun buildValueClass(className: ClassName, block: KotlinValueClassBuilder.() -> Unit) = KotlinValueClassBuilder.builder(className).also(block).build()

  @Suppress(CLASS_NAME)
  object builder {
    fun annotationBuilder(type: ClassName) = KotlinAnnotationSpecBuilder.builder(type)
    fun annotationClassBuilder(className: ClassName) = KotlinAnnotationClassSpecBuilder.builder(className)
    fun constructorPropertyBuilder(name: String, type: TypeName) = KotlinConstructorPropertySpecBuilder.builder(name, type)
    fun fileBuilder(className: ClassName) = KotlinFileSpecBuilder.builder(className)
    fun valueClassBuilder(className: ClassName) = KotlinValueClassSpecBuilder.builder(className)
  }

  @Suppress(CLASS_NAME)
  object annotation {

    data class GeneratedAnnotation(
      val value: String = KotlinCodeGeneration::class.asTypeName().toString(),
      val date: Instant = Instant.now(),
      val comments: List<String> = emptyList()
    ) : KotlinAnnotationSpecSupplier {

      fun generator(type: KClass<*>) = copy(value = type.asTypeName().toString())
      fun date(instant: Instant) = copy(date = instant)
      fun comment(comment: Pair<String, String>) = copy(comments = this.comments + "${comment.first} = ${comment.second}")

      override fun spec(): KotlinAnnotationSpec = buildAnnotation(Generated::class) {
        addStringMember("value", value)
        addStringMember("date", date.toString())

        if (comments.isNotEmpty()) {
          addStringMember("comments", comments.joinToString(separator = "; "))
        }
      }
    }

  }

  @Suppress(CLASS_NAME)
  object typeSpec {

    fun TypeSpec.hasModifier(modifier: KModifier) = this.modifiers.contains(modifier)

    val TypeSpec.isDataClass: Boolean get() = hasModifier(KModifier.DATA)
    val TypeSpec.isValueClass: Boolean get() = hasModifier(KModifier.VALUE)
  }

  @Suppress(CLASS_NAME)
  object name {
    fun MemberName.asCodeBlock(): CodeBlock = buildCodeBlock("%M", this)

    fun Collection<MemberName>.asCodeBlock(): CodeBlock = this.map{it.asCodeBlock()}.joinToCode(prefix = "[", suffix = "]")

    fun Enum<*>.asMemberName(): MemberName = this::class.asClassName().member(this.name)

    operator fun ClassName.plus(suffix: String?): ClassName = ClassName(this.packageName, this.simpleNames)

    fun TypeName.nullable(nullable: Boolean = true): TypeName = this.copy(nullable = nullable)
  }
}
