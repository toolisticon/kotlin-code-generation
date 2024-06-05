package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationSpecBuilder
import io.toolisticon.kotlin.generation.builder.KotlinConstructorPropertyBuilder
import io.toolisticon.kotlin.generation.builder.KotlinDataClassBuilder
import io.toolisticon.kotlin.generation.builder.KotlinValueClassBuilder
import io.toolisticon.kotlin.generation.spec.KotlinDataClassSpec

object KotlinCodeGeneration {


  inline fun buildAnnotation(type: ClassName, block: KotlinAnnotationSpecBuilder.() -> Unit) = KotlinAnnotationSpecBuilder.builder(type).also(block).build()
  inline fun buildDataClass(className: ClassName, block: KotlinDataClassBuilder.() -> Unit): KotlinDataClassSpec = KotlinDataClassBuilder.builder(className).also(block).build()
  inline fun buildConstructorProperty(name: String, type: TypeName, block: KotlinConstructorPropertyBuilder.() -> Unit) = KotlinConstructorPropertyBuilder.builder(name, type).also(block).build()
  inline fun buildValueClass(className: ClassName, block: KotlinValueClassBuilder.() -> Unit) = KotlinValueClassBuilder.builder(className).also(block).build()

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

}
