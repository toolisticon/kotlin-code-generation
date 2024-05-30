package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.KotlinCodeGeneration.Supressions.CLASS_NAME
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationBuilder

object KotlinCodeGeneration {

  val annotationBuilder = KotlinAnnotationBuilder.builder


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
