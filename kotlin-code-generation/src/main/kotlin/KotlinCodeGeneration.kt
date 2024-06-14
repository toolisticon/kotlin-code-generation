package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME

object KotlinCodeGeneration {

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

  internal object Supressions {
    const val CLASS_NAME = "ClassName"
  }

  object Annotation {


//    fun serializableAnnotation(serializerClass: Avro4kSerializerKClass) = AnnotationSpec.builder(Serializable::class)
//      .addMember("with = %T::class", serializerClass)
//      .build()

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
