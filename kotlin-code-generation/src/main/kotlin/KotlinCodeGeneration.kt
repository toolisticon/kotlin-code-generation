package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.*
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.annotationClassBuilder
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.builder.constructorPropertyBuilder
import io.toolisticon.kotlin.generation.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.*
import io.toolisticon.kotlin.generation.spec.*
import jakarta.annotation.Generated
import java.time.Instant
import kotlin.reflect.KClass

object KotlinCodeGeneration {

  @JvmStatic
  fun buildAnnotation(kclass: KClass<*>, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = buildAnnotation(kclass.asClassName(), block)

  @JvmStatic
  fun buildAnnotation(className: ClassName, block: KotlinAnnotationSpecBuilderReceiver = {}): KotlinAnnotationSpec = annotationBuilder(className).also(block).build()

  @JvmStatic
  fun buildAnnotationClass(className: ClassName, block: KotlinAnnotationClassSpecBuilderReceiver = {}): KotlinAnnotationClassSpec = annotationClassBuilder(className).also(block).build()

  @JvmStatic
  fun buildConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = constructorPropertyBuilder(name, type).also(block).build()

  @JvmStatic
  fun buildConstructorProperty(name: String, type: KClass<*>, block: KotlinConstructorPropertySpecBuilderReceiver = {}) = KotlinCodeGeneration.buildConstructorProperty(name, type.asTypeName(), block)

  @JvmStatic
  fun buildDataClass(className: ClassName, block: KotlinDataClassSpecBuilderReceiver = {}): KotlinDataClassSpec = KotlinDataClassSpecBuilder.builder(className).also(block).build()

  @JvmStatic
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
    @JvmStatic
    fun annotationBuilder(type: ClassName) = KotlinAnnotationSpecBuilder.builder(type)

    @JvmStatic
    fun annotationClassBuilder(className: ClassName) = KotlinAnnotationClassSpecBuilder.builder(className)

    @JvmStatic
    fun constructorPropertyBuilder(name: String, type: TypeName) = KotlinConstructorPropertySpecBuilder.builder(name, type)

    @JvmStatic
    fun valueClassBuilder(className: ClassName) = KotlinValueClassSpecBuilder.builder(className)

  }

  @Suppress(CLASS_NAME)
  object annotation {

    data class GeneratedAnnotation(
      val value: String = KotlinCodeGeneration::class.asTypeName().toString(),
      val date: String = Instant.now().toString(),
      val comments: List<String> = emptyList()
    ) : KotlinAnnotationSpecSupplier {

      fun generator(type: KClass<*>) = copy(value = type.asTypeName().toString())
      fun date(instant: Instant) = copy(date = instant.toString())
      fun comment(comment: Pair<String, String>) = copy(comments = this.comments + "${comment.first} = ${comment.second}")

      override fun spec(): KotlinAnnotationSpec = buildAnnotation(Generated::class) {
        addStringMember("value", value)
        addStringMember("date", date)

        if (comments.isNotEmpty()) {
          addStringMember("comments", comments.joinToString(separator = ", "))
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
  object typeName {

    operator fun ClassName.plus(suffix: String?) = ClassName(this.packageName, this.simpleNames)

    fun TypeName.nullable(nullable: Boolean = true): TypeName = this.copy(nullable = nullable)
  }
}
