package io.toolisticon.kotlin.generation

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import io.toolisticon.kotlin.generation.builder.KotlinAnnotationBuilder
import kotlinx.serialization.Serializable

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

}
